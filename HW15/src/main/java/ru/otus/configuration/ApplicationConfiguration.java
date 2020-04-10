package ru.otus.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.*;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.SubscribableChannel;
import ru.otus.domain.InfectedTourist;
import ru.otus.domain.Tourist;

import java.util.concurrent.Executors;

@Configuration
public class ApplicationConfiguration {

	@Value("${application.thread-count:1}")
	private int threadCount;

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
				.channel(MessageChannels.executor(Executors.newFixedThreadPool(threadCount)))
				.handle("covid19Detector", "check")
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
		return flow -> flow.handle("hospitalRegistry", "register");
	}

	@Bean
	IntegrationFlow bridgeFlow() {
		return IntegrationFlowDefinition::bridge;
	}
}
