package ru.otus.service;

import com.github.javafaker.Faker;
import ru.otus.domain.Country;
import ru.otus.domain.Tourist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class TrafficGenerator {

	private final static Faker FAKER = new Faker();

	private final static Random RANDOM = new Random();

	public static Collection<Tourist> generate(int volume)  {
		final Collection<Tourist> traffic = new ArrayList<>(volume);

		for (int i = 0; i < volume; i++) {
			traffic.add(new Tourist(FAKER.name().fullName(), getCountry()));
		}

		return traffic;
	}

	private static Country getCountry() {
		final int next = RANDOM.nextInt(Country.values().length);
		return Country.values()[next];
	}
}
