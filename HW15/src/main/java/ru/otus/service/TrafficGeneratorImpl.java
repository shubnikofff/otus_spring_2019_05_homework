package ru.otus.service;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.domain.Country;
import ru.otus.domain.Tourist;

import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TrafficGeneratorImpl implements TrafficGenerator {

	@Value("${application.tourist-min-quantity:10}")
	private int touristMinQuantity;

	@Value("${application.tourist-max-quantity:10}")
	private int touristMaxQuantity;

	private final Faker faker = new Faker();

	private final Random random = new Random();

	public Collection<Tourist> generate() {
		return Stream.generate(() -> new Tourist(faker.name().fullName(), getCountry()))
				.limit(random.nextInt((touristMaxQuantity - touristMinQuantity) + 1) + touristMinQuantity)
				.collect(Collectors.toList());
	}

	private Country getCountry() {
		final int next = random.nextInt(Country.values().length);
		return Country.values()[next];
	}
}
