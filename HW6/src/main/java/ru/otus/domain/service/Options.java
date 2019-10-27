package ru.otus.domain.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Options<T> {
	private final Map<Integer, T> optionMap;

	public Options(List<T> optionList) {
		final AtomicInteger index = new AtomicInteger();
		optionMap = optionList.stream().collect(Collectors.toMap(t -> index.incrementAndGet(), t -> t));
	}

	public Stream<Map.Entry<Integer, T>> stream() {
		return optionMap.entrySet().stream();
	}

	public boolean contains(Integer key) {
		return optionMap.containsKey(key);
	}

	public T get(Integer key) {
		return optionMap.get(key);
	}
}
