package ru.otus.service;

import ru.otus.domain.Question;
import ru.otus.domain.Test;

public interface FrontendService {
	String getFirstName();

	String getLastName();

	int getAnswer(Question question);

	void printResult(Test test);
}
