package ru.otus.domain.service;

import java.util.List;

public interface IOService {
	Long getOneOf(List<Long> list, String message);

	void print(String string);
}
