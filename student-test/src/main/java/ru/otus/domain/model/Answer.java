package ru.otus.domain.model;

public class Answer {
	private final String id;
	private final String wording;

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
