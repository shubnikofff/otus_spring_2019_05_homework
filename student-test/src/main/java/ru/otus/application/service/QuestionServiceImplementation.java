package ru.otus.application.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.model.Question;
import ru.otus.domain.service.QuestionService;

@Service
public class QuestionServiceImplementation implements QuestionService {
	@Override
	public String stringifyQuestion(Question question) {
		final StringBuilder stringBuilder = new StringBuilder(question.getWording() + "\n");

		question.getAnswers().forEach((integer, answer) -> stringBuilder
				.append("\t")
				.append(answer.getId())
				.append(". ")
				.append(answer.getWording())
				.append("\n"));

		return stringBuilder.toString();
	}
}
