package ru.otus.application;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.domain.service.QuestionDao;
import ru.otus.domain.model.Test;
import ru.otus.domain.service.FrontendService;
import ru.otus.domain.service.TestService;

import java.io.IOException;

@ShellComponent
public class Application {
	private final QuestionDao questionDao;
	private final FrontendService frontendService;
	private final TestService testService;
	private String firstName;
	private String lastName;

	public Application(QuestionDao questionDao, FrontendService frontendService, TestService testService) {
		this.questionDao = questionDao;
		this.frontendService = frontendService;
		this.testService = testService;
	}

	@ShellMethod(value = "Login command", key = {"l", "login"})
	String login() {
		firstName = frontendService.getFirstName();
		lastName = frontendService.getLastName();
		return "Welcome " + firstName + " " + lastName + ". Now you can start test.";
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
