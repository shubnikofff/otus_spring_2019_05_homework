package ru.otus.domain.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question {
	private final String wording;
	private final String correctAnswer;
	private final Map<String, Answer> answerMap;

	public Question(String wording, String correctAnswer, List<Answer> answerList) {
		this.wording = wording;
		this.correctAnswer = correctAnswer;
		answerMap = new HashMap<>(answerList.size());
		answerList.forEach(answer -> answerMap.put(answer.getId(), answer));
	}

	public String getWording() {
		return wording;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public Map<String, Answer> getAnswers() {
		return answerMap;
	}

	boolean isAnswerCorrect(String answer) {
		return answer.toLowerCase().equals(correctAnswer.toLowerCase());
	}
}
