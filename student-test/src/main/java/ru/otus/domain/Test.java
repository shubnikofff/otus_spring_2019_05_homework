package ru.otus.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
	private String firstName;
	private String lastName;
	private List<Question> questions;
	private Map<Question, Integer> answers;

	public Test(String firstName, String lastName, List<Question> questions) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.questions = questions;
		answers = new HashMap<>(questions.size());
	}

	public void setAnswer(Question question, int answer) {
		answers.put(question, answer);
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public Map<Question, Integer> getAnswers() {
		return answers;
	}

	public List<Question> getQuestions() {
		return questions;
	}
}
