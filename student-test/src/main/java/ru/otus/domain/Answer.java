package ru.otus.domain;

public class Answer {
	private int id;
	private String wording;

	public Answer(int id, String wording) {
		this.id = id;
		this.wording = wording;
	}

	public int getId() {
		return id;
	}

	public String getWording() {
		return wording;
	}
}
