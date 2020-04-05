package ru.otus.service;

import com.github.javafaker.Faker;
import ru.otus.domain.Country;
import ru.otus.domain.Tourist;

import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrafficGenerator {

	private final static Faker FAKER = new Faker();

	private final static Random RANDOM = new Random();

	public static Collection<Tourist> generate(int volume)  {
		return Stream.generate(() -> new Tourist(FAKER.name().fullName(), getCountry()))
				.limit(volume)
				.collect(Collectors.toList());
	}

	private static Country getCountry() {
		final int next = RANDOM.nextInt(Country.values().length);
		return Country.values()[next];
	}
}
