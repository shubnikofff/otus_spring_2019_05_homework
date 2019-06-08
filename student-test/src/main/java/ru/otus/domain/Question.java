package ru.otus.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question {
	private String wording;
	private String correctAnswer;
	private Map<String, Answer> answerMap;

	public Question(String wording, String correctAnswer, List<Answer> answerList) {
		this.wording = wording;
		this.correctAnswer = correctAnswer;
		answerMap = new HashMap<>(answerList.size());
		answerList.forEach(answer -> answerMap.put(answer.getId(), answer));
	}

	public boolean isAnswerValid(String answerId) {
		if (answerMap.isEmpty()) {
			return true;
		}

		return answerMap.containsKey(answerId);
	}

	public boolean isAnswerCorrect(String answer) {
		return answer.toLowerCase().equals(correctAnswer.toLowerCase());
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
