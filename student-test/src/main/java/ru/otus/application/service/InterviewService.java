package ru.otus.application.service;

import ru.otus.domain.model.Question;
import ru.otus.domain.model.TestResults;

import java.util.concurrent.atomic.AtomicReference;

class InterviewService {
	static boolean isAnswerValid(String answer, Question question) {
		if (question.getAnswerOptions().isEmpty()) {
			return true;
		}

		return question.getAnswerOptions().containsKey(answer);
	}

	static float getSuccessPercentage(TestResults testResults) {
		AtomicReference<Float> numberOfCorrectAnswers = new AtomicReference<>((float) 0);

		testResults.forEach(questionAnswerEntry -> {
			if (isAnswerCorrect(questionAnswerEntry.getKey(), questionAnswerEntry.getValue())) {
				numberOfCorrectAnswers.getAndSet(numberOfCorrectAnswers.get() + 1);
			}
		});

		return numberOfCorrectAnswers.get() / testResults.size() * 100;
	}

	private static boolean isAnswerCorrect(Question question, String answer) {
		return answer.toLowerCase().equals(question.getCorrectAnswer().toLowerCase());
	}
}
