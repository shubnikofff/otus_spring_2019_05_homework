package ru.otus.domain.service;

import ru.otus.domain.model.Options;

public interface IOService {
	<T> T getOneOf(Options<T> options, String message);

	void print(String string);
}
