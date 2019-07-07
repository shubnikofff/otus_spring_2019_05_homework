package ru.otus.application.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.model.Question;
import ru.otus.domain.model.Test;
import ru.otus.domain.service.TestService;

import java.util.Map;

@Service
public class TestServiceImplementation implements TestService {
	@Override
	public boolean isAnswerValid(Question question, String answer) {
		if (question.getAnswers().isEmpty()) {
			return true;
		}

		return question.getAnswers().containsKey(answer);
	}

	@Override
	public float getSuccessPercentage(Test test) {
		float numberOfCorrectAnswers = 0;
		for (Map.Entry<Question, String> questionAnswerEntry : test.getAnswers().entrySet()) {
			if (isAnswerCorrect(questionAnswerEntry.getKey(), questionAnswerEntry.getValue())) {
				numberOfCorrectAnswers++;
			}
		}

		return numberOfCorrectAnswers / test.getAnswers().size() * 100;
	}

	private static boolean isAnswerCorrect(Question question, String answer) {
		return answer.toLowerCase().equals(question.getCorrectAnswer().toLowerCase());
	}
}
