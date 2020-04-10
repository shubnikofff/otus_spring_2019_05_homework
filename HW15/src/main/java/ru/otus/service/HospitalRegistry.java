package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.domain.Tourist;

@Service
public class HospitalRegistry {

	private final Logger logger = LoggerFactory.getLogger(HospitalRegistry.class.getName());

	public Tourist register(Tourist tourist) {
		logger.info("Register " + tourist.getName());
		return tourist;
	}
}
