package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.domain.Tourist;

@Service
public class HospitalRegistryImpl implements HospitalRegistry {

	private final Logger logger = LoggerFactory.getLogger(HospitalRegistryImpl.class.getName());

	public Tourist register(Tourist tourist) {
		logger.info("Register " + tourist);
		return tourist;
	}
}
