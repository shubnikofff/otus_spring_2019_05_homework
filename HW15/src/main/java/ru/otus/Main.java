package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import ru.otus.domain.InfectedTourist;
import ru.otus.domain.Tourist;
import ru.otus.service.ArrivalTerminal;
import ru.otus.service.TrafficGenerator;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;

@SuppressWarnings("InfiniteLoopStatement")
@SpringBootApplication
@IntegrationComponentScan
public class Main {

	private static final int SLEEP_TIME_MS = 1000;

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class.getName());

	public static void main(String[] args) throws InterruptedException {
		final ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
		final ArrivalTerminal terminal = context.getBean(ArrivalTerminal.class);
		final TrafficGenerator trafficGenerator = context.getBean(TrafficGenerator.class);

		while (true) {
			Thread.sleep(SLEEP_TIME_MS);

			Collection<Tourist> traffic = trafficGenerator.generate();
			LOGGER.info("Accept " + traffic.size() + " tourists");

			final Instant start = Instant.now();
			Collection<Tourist> outputTraffic = terminal.accept(traffic);
			final Instant finish = Instant.now();

			final long infectedQuantity = outputTraffic.stream().filter(tourist -> tourist instanceof InfectedTourist).count();
			LOGGER.info(infectedQuantity + " of " + traffic.size() + " infected!");

			LOGGER.info("Execution time: " + Duration.between(start, finish).toMillis() + "ms");
		}
	}
}
