package ru.otus.application.service.io;

import ru.otus.application.service.frontend.Options;

public interface IOService {

	<T> T getOneOf(Options<T> options, String message);

	void print(String string);
}
