package ru.otus.domain;

import java.util.List;

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

	public int getId() {
		return id;
	}

	public String getWording() {
		return wording;
	}

	public int getCorrectAnswerId() {
		return correctAnswerId;
	}

	public List<Answer> getAnswerList() {
		return answerList;
	}
}
