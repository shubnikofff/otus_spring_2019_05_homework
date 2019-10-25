package ru.otus.domain.service;

import ru.otus.domain.model.Options;

import java.util.List;

public interface Stringifier<E> {
	String stringify(E e);

	String stringify(List<E> list);

	String stringify(Options<E> options);
}
