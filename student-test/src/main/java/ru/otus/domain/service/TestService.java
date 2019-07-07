package ru.otus.domain.service;

import ru.otus.domain.model.Question;

public interface TestService {
	boolean isAnswerValid(Question question, String answer);
}
