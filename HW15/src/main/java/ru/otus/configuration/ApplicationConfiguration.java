package ru.otus.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.*;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.SubscribableChannel;
import ru.otus.domain.InfectedTourist;
import ru.otus.domain.Tourist;
import ru.otus.service.Covid19Detector;
import ru.otus.service.HospitalRegistry;

import java.util.concurrent.Executors;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

	private static final String CHANNEL_ENTRANCE = "entrance";

	private static final String CHANNEL_EXIT = "exit";

	private static final String METHOD_CHECK = "check";

	private static final String METHOD_REGISTER = "register";

	private static final int CHANNEL_ENTRANCE_CAPACITY = 5;

	private static final int POLLER_PERIOD = 100;

	private static final int MAX_MESSAGE_PER_POLL = 2;

	@Value("${application.thread-quantity:10}")
	private int threadQuantity;

	private final Covid19Detector covid19Detector;

	private final HospitalRegistry hospitalRegistry;

	@Bean
	public QueueChannel entrance() {
		return MessageChannels.queue(CHANNEL_ENTRANCE_CAPACITY).get();
	}

	@Bean
	public SubscribableChannel exit() {
		return MessageChannels.publishSubscribe().get();
	}

	@Bean(name = PollerMetadata.DEFAULT_POLLER)
	public PollerMetadata poller() {
		return Pollers.fixedRate(POLLER_PERIOD).maxMessagesPerPoll(MAX_MESSAGE_PER_POLL).get();
	}

	@Bean
	public IntegrationFlow checkFlow() {
		return IntegrationFlows.from(CHANNEL_ENTRANCE)
				.split()
				.channel(MessageChannels.executor(Executors.newFixedThreadPool(threadQuantity)))
				.handle(covid19Detector, METHOD_CHECK)
				.aggregate()
				.split()
				.<Tourist, Boolean>route(tourist -> tourist instanceof InfectedTourist, maping ->
						maping
								.subFlowMapping(true, sf -> sf.gateway(infectedFlow()))
								.subFlowMapping(false, sf -> sf.gateway(bridgeFlow()))
								.defaultOutputToParentFlow()
				)
				.aggregate()
				.channel(CHANNEL_EXIT)
				.get();
	}

	@Bean
	IntegrationFlow infectedFlow() {
		return flow -> flow.handle(hospitalRegistry, METHOD_REGISTER);
	}

	@Bean
	IntegrationFlow bridgeFlow() {
		return IntegrationFlowDefinition::bridge;
	}
}
