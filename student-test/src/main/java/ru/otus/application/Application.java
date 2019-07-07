package ru.otus.application;

import org.springframework.stereotype.Service;
import ru.otus.domain.service.QuestionDao;
import ru.otus.domain.model.Test;
import ru.otus.domain.service.FrontendService;
import ru.otus.domain.service.TestService;

import java.io.IOException;

@Service
public class Application {
	private final QuestionDao questionDao;
	private final FrontendService frontendService;
	private final TestService testService;

	public Application(QuestionDao questionDao, FrontendService frontendService, TestService testService) {
		this.questionDao = questionDao;
		this.frontendService = frontendService;
		this.testService = testService;
	}

	public void run() throws IOException {
		final Test test = new Test(frontendService.getFirstName(), frontendService.getLastName());
		questionDao.getQuestions().forEach(question -> {
			String answer;
			do {
				answer = frontendService.getAnswer(question);
			} while (!testService.isAnswerValid(question, answer));

			test.setAnswer(question, answer);
		});
		frontendService.printResult(test);
	}
}
