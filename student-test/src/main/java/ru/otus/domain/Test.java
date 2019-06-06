package ru.otus.domain;

import java.util.HashMap;
import java.util.Map;

public class Test {
	private String firstName;
	private String lastName;
	private Map<Question, Integer> answers = new HashMap<>();

	public Test(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public void setAnswer(Question question, int answer) {
		answers.put(question, answer);
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}
}
