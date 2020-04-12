package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.domain.InfectedTourist;
import ru.otus.domain.Tourist;

import java.util.Random;

@Service("covid19Detector")
public class Covid19DetectorImpl implements Covid19Detector {

	private final static int COVID19_MAX_RATE = 100;

	private final static int SLEEP_TIME_MS = 2000;

	private final Logger logger = LoggerFactory.getLogger(Covid19DetectorImpl.class.getName());

	private final Random random = new Random();

	public Tourist check(Tourist tourist) throws InterruptedException {
		logger.info("Checking " + tourist);

		final boolean infected = random.nextInt(COVID19_MAX_RATE + 1) < tourist.getArrivedFrom().getCovid19Rate();
		Thread.sleep(SLEEP_TIME_MS);

		return infected ? new InfectedTourist(tourist.getName(), tourist.getArrivedFrom()) : tourist;
	}
}
