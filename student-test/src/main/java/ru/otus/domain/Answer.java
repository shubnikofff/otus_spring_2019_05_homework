package ru.otus.domain;

public class Answer {
	private String id;
	private String wording;

	public Answer(String id, String wording) {
		this.id = id;
		this.wording = wording;
	}

	public String getId() {
		return id;
	}

	public String getWording() {
		return wording;
	}
}
