package ru.otus.application;

import ru.otus.dao.QuestionDao;
import ru.otus.domain.Test;
import ru.otus.service.FrontendService;

import java.io.IOException;

public class Application {
	private final QuestionDao questionDao;
	private final FrontendService frontendService;

	public Application(QuestionDao questionDao, FrontendService frontendService) {
		this.questionDao = questionDao;
		this.frontendService = frontendService;
	}

	public void run() throws IOException {
		final Test test = new Test(frontendService.getFirstName(), frontendService.getLastName());
		questionDao.getQuestions().forEach(question -> {
			String answer;
			do {
				answer = frontendService.getAnswer(question);
			} while (!question.isAnswerValid(answer));

			test.setAnswer(question, answer);
		});
		frontendService.printResult(test);
	}
}
