package ru.otus.domain.service;

public interface IOService {
	<T> T getOneOf(Options<T> options, String message);

	void print(String string);
}
