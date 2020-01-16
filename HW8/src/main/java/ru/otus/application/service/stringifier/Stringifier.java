package ru.otus.application.service.stringifier;

import ru.otus.application.service.frontend.Options;

import java.util.List;

public interface Stringifier<E> {
	String stringify(E e);

	String stringify(List<E> list);

	String stringify(Options<E> options);
}
