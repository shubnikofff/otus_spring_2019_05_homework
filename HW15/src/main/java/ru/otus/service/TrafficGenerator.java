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
public class TrafficGenerator {

	@Value("${application.tourist-min-quantity:10}")
	private int touristMinQuantity;

	@Value("${application.tourist-max-quantity:10}")
	private int touristMaxQuantity;

	private final static Faker FAKER = new Faker();

	private final static Random RANDOM = new Random();

	public Collection<Tourist> generate() {
		return Stream.generate(() -> new Tourist(FAKER.name().fullName(), getCountry()))
				.limit(RANDOM.nextInt((touristMaxQuantity - touristMinQuantity) + 1) + touristMinQuantity)
				.collect(Collectors.toList());
	}

	private static Country getCountry() {
		final int next = RANDOM.nextInt(Country.values().length);
		return Country.values()[next];
	}
}
