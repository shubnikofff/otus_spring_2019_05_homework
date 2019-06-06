package ru.otus.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question {
	private int id;
	private String wording;
	private int correctAnswerId;
	private Map<Integer, Answer> answerMap;

	public Question(int id, String wording, int correctAnswerId, List<Answer> answerList) {
		this.id = id;
		this.wording = wording;
		this.correctAnswerId = correctAnswerId;
		answerMap = new HashMap<>(answerList.size());
		answerList.forEach(answer -> answerMap.put(answer.getId(), answer));
	}

	public boolean answerExists(int answerId) {
		return answerMap.containsKey(answerId);
	}

	@Override
	public String toString() {
		final StringBuilder stringBuilder = new StringBuilder(id + ") " + wording + "\n");

		answerMap.forEach((integer, answer) -> stringBuilder
				.append("\t")
				.append(answer.getId())
				.append(". ")
				.append(answer.getWording())
				.append("\n"));

		return stringBuilder.toString();
	}
}
