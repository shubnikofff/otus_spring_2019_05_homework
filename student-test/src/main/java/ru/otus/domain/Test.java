package ru.otus.domain;

import java.util.HashMap;
import java.util.Map;

public class Test {
	private String firstName;
	private String lastName;
	private Map<Question, String> answers = new HashMap<>();

	public Test(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public void setAnswer(Question question, String answer) {
		answers.put(question, answer);
	}
}
