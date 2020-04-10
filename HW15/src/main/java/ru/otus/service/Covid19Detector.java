package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.domain.InfectedTourist;
import ru.otus.domain.Tourist;

@Service
public class Covid19Detector {

	private final Logger logger = LoggerFactory.getLogger(Covid19Detector.class.getName());

	public Tourist check(Tourist tourist) throws InterruptedException {
		logger.info("Checking " + tourist);

		final boolean infected = Math.random() * 100 < tourist.getArrivedFrom().getCovid19Rate();
		Thread.sleep(2000);

		return infected ? new InfectedTourist(tourist.getName(), tourist.getArrivedFrom()) : tourist;
	}
}
