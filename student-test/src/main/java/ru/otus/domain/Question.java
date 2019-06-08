package ru.otus.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question {
	private String wording;
	private int correctAnswerId;
	private Map<Integer, Answer> answerMap;

	public Question(String wording, int correctAnswerId, List<Answer> answerList) {
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
		final StringBuilder stringBuilder = new StringBuilder(wording + "\n");

		answerMap.forEach((integer, answer) -> stringBuilder
				.append("\t")
				.append(answer.getId())
				.append(". ")
				.append(answer.getWording())
				.append("\n"));

		return stringBuilder.toString();
	}
}
