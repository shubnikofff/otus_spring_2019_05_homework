package ru.otus.application;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.domain.model.TestResults;
import ru.otus.domain.service.QuestionDao;
import ru.otus.domain.service.FrontendService;

import java.io.IOException;

@ShellComponent
public class Application {
	private final QuestionDao questionDao;
	private final FrontendService frontendService;
	private String firstName;
	private String lastName;
	private TestResults testResults;

	public Application(QuestionDao questionDao, FrontendService frontendService) {
		this.questionDao = questionDao;
		this.frontendService = frontendService;
	}

	@ShellMethod(value = "Login command", key = {"l", "login"})
	void login() {
		firstName = frontendService.getFirstName();
		lastName = frontendService.getLastName();
		frontendService.greeting();
	}

	@ShellMethod(value = "Run test", key = {"r", "run"})
	@ShellMethodAvailability(value = "isRunTestCommandAvailable")
	void runTest() throws IOException {
		testResults = frontendService.getTestResults(questionDao.getQuestions());
	}

	@ShellMethod(value = "Print result", key = {"p", "print"})
	@ShellMethodAvailability(value = "isPrintResultCommandAvailable")
	void printResult() {
		frontendService.printTestResults(firstName, lastName, testResults);
	}

	private Availability isRunTestCommandAvailable() {
		return firstName == null && lastName == null
				? Availability.unavailable("test not logged in")
				: Availability.available();
	}

	private Availability isPrintResultCommandAvailable() {
		return firstName == null || lastName == null || testResults == null
				? Availability.unavailable("no result for print")
				: Availability.available();
	}
}
