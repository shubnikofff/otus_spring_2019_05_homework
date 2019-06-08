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

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public float getSuccessPercentage() {
		float numberOfCorrectAnswers = 0;
		for (Map.Entry<Question, String> questionAnswerEntry : answers.entrySet()) {
			if (questionAnswerEntry.getKey().isAnswerCorrect(questionAnswerEntry.getValue())) {
				numberOfCorrectAnswers++;
			}
		}

		return numberOfCorrectAnswers / answers.size() * 100;
	}

	public void setAnswer(Question question, String answer) {
		answers.put(question, answer);
	}
}
