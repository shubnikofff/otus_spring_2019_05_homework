package ru.otus.application.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.application.configuration.ApplicationProperties;
import ru.otus.domain.model.Question;
import ru.otus.domain.model.TestResults;
import ru.otus.domain.service.FrontendService;
import ru.otus.domain.service.IOService;

import java.util.List;
import java.util.Locale;

@Service
public class FrontendServiceImplementation implements FrontendService {
	private final IOService ioService;
	private final MessageSource messageSource;
	private final Locale locale;

	public FrontendServiceImplementation(
			IOService ioService,
			MessageSource messageSource,
			ApplicationProperties applicationProperties
	) {
		this.ioService = ioService;
		this.messageSource = messageSource;
		this.locale = new Locale(applicationProperties.getLocale());
	}

	@Override
	public String getFirstName() {
		ioService.print(messageSource.getMessage("enter.name", null, locale) + ": ");
		return ioService.getInput();
	}

	@Override
	public String getLastName() {
		ioService.print(messageSource.getMessage("enter.surname", null, locale) + ": ");
		return ioService.getInput();
	}

	@Override
	public void greeting() {
		ioService.print(messageSource.getMessage("greeting", null, locale) + "\n");
	}

	@Override
	public TestResults getTestResults(List<Question> questions) {
		final TestResults testResults = new TestResults();

		questions.forEach(question -> testResults.addAnswer(question, getAnswer(question)));

		return testResults;
	}

	@Override
	public void printTestResults(String firstName, String lastName, TestResults testResults) {
		final String result = messageSource.getMessage("test.passed.by.student", null, locale) + ": " +
				firstName + " " + lastName + "\n" +
				messageSource.getMessage("percentage.of.correct.answers", null, locale) + ": " +
				InterviewService.getSuccessPercentage(testResults) + "%\n";

		ioService.print(result);
	}

	private String getAnswer(Question question) {
		String answer;

		do {
			ioService.print(question.toString());
			answer = ioService.getInput();
		} while (!InterviewService.isAnswerValid(answer, question));

		return answer;
	}
}
