package ru.otus.application.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.model.Question;
import ru.otus.domain.service.TestService;

@Service
public class TestServiceImplementation implements TestService {
	@Override
	public boolean isAnswerValid(Question question, String answer) {
		if (question.getAnswers().isEmpty()) {
			return true;
		}

		return question.getAnswers().containsKey(answer);
	}
}
