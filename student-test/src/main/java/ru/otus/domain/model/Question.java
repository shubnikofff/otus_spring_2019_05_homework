package ru.otus.domain.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question {
	private final String wording;
	private final String correctAnswer;
	private final Map<String, Answer> answerOptions;

	public Question(String wording, String correctAnswer, List<Answer> answerList) {
		this.wording = wording;
		this.correctAnswer = correctAnswer;
		answerOptions = new HashMap<>(answerList.size());
		answerList.forEach(answer -> answerOptions.put(answer.getId(), answer));
	}

	public String getWording() {
		return wording;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public Map<String, Answer> getAnswerOptions() {
		return answerOptions;
	}

	@Override
	public String toString() {
		final StringBuilder stringBuilder = new StringBuilder(wording + "\n");

		answerOptions.forEach((integer, answer) -> stringBuilder
				.append("\t")
				.append(answer.getId())
				.append(". ")
				.append(answer.getWording())
				.append("\n"));

		return stringBuilder.toString();
	}
}
