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

	@Value("${application.thread-quantity:10}")
	private int threadQuantity;

	private final Covid19Detector covid19Detector;

	private final HospitalRegistry hospitalRegistry;

	@Bean
	public QueueChannel entrance() {
		return MessageChannels.queue(5).get();
	}

	@Bean
	public SubscribableChannel exit() {
		return MessageChannels.publishSubscribe().get();
	}

	@Bean(name = PollerMetadata.DEFAULT_POLLER)
	public PollerMetadata poller() {
		return Pollers.fixedRate(100).maxMessagesPerPoll(2).get();
	}

	@Bean
	public IntegrationFlow checkFlow() {
		return IntegrationFlows.from("entrance")
				.split()
				.channel(MessageChannels.executor(Executors.newFixedThreadPool(threadQuantity)))
				.handle(covid19Detector, "check")
				.aggregate()
				.split()
				.<Tourist, Boolean>route(tourist -> tourist instanceof InfectedTourist, maping ->
						maping
								.subFlowMapping(true, sf -> sf.gateway(infectedFlow()))
								.subFlowMapping(false, sf -> sf.gateway(bridgeFlow()))
								.defaultOutputToParentFlow()
				)
				.aggregate()
				.channel("exit")
				.get();
	}

	@Bean
	IntegrationFlow infectedFlow() {
		return flow -> flow.handle(hospitalRegistry, "register");
	}

	@Bean
	IntegrationFlow bridgeFlow() {
		return IntegrationFlowDefinition::bridge;
	}
}
