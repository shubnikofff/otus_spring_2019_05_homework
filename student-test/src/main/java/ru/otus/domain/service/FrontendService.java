package ru.otus.domain.service;

import ru.otus.domain.model.Question;
import ru.otus.domain.model.Test;

public interface FrontendService {
	String getFirstName();

	String getLastName();

	String getAnswer(Question question);

	void printResult(Test test);
}
