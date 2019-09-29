package ru.otus.domain.service;

import ru.otus.domain.model.Question;
import ru.otus.domain.model.TestResults;

import java.util.List;

public interface FrontendService {
	String getFirstName();

	String getLastName();

	void greeting();

	TestResults getTestResults(List<Question> questions);

	void printTestResults(String firstName, String lastName, TestResults testResults);
}
