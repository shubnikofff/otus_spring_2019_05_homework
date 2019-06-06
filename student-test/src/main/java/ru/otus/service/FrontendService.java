package ru.otus.service;

import ru.otus.domain.Question;

public interface FrontendService {
	String getFirstName();

	String getLastName();

	int getAnswer(Question question);
}
