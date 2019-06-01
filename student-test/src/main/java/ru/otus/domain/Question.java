package ru.otus.domain;

import java.util.Map;

public class Question {
	private int id;
	private String wording;
	private int correctAnswerId;
	private Map<Integer, Answer> answerMap;

	public Question(int id, String wording, int correctAnswerId, Map<Integer, Answer> answerMap) {
		this.id = id;
		this.wording = wording;
		this.correctAnswerId = correctAnswerId;
		this.answerMap = answerMap;
	}
}
