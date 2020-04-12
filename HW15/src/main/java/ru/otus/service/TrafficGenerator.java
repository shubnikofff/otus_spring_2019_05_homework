package ru.otus.service;

import ru.otus.domain.Tourist;

import java.util.Collection;

public interface TrafficGenerator {

	Collection<Tourist> generate();
}
