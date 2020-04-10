package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.Tourist;

@Service
public class Covid19Detector {

	public void check(Tourist tourist) {
		final boolean infected = Math.random() * 100 < tourist.getArrivedFrom().getCovid19Rate();
		tourist.setInfected(infected);
	}
}
