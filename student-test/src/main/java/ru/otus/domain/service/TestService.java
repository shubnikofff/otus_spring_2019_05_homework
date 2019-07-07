package ru.otus.domain.service;

import ru.otus.domain.model.Question;
import ru.otus.domain.model.Test;

public interface TestService {
	boolean isAnswerValid(Question question, String answer);

	float getSuccessPercentage(Test test);
}
