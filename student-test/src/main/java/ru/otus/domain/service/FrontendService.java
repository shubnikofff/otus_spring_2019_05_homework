package ru.otus.domain.service;

import ru.otus.domain.model.Question;

import java.util.Map;

public interface FrontendService {
	String getFirstName();

	String getLastName();

	void greeting();

	String getAnswer(Question question);

	void printResult(String name, Map<Question, String> answerMap);
}
