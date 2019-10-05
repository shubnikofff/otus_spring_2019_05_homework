package ru.otus.domain.service;

import java.util.Map;

public interface IOService<E> {
	String getUserInput(String message);

	void print(String string);

	void printMap(Map<Long, E> map);

	E getOneFromMap(Map<Long, E> map, String message);
}
