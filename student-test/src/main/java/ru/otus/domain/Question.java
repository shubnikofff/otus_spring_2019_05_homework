package ru.otus.domain;

import java.util.List;
import java.util.Map;

public class Question {
	private int id;
	private String wording;
	private int correctAnswerId;
	private List<Answer> answerList;

	public Question(int id, String wording, int correctAnswerId, List<Answer> answerList) {
		this.id = id;
		this.wording = wording;
		this.correctAnswerId = correctAnswerId;
		this.answerList = answerList;
	}
}
