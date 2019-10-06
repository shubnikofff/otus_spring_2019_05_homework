package ru.otus.domain.service;

import java.util.List;

public interface Stringifier<E> {
	String stringify(E e);

	String stringify(List<E> list);
}
