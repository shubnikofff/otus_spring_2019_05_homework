package ru.otus.application.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.application.configuration.ApplicationProperties;
import ru.otus.application.utility.ConsoleUtility;
import ru.otus.domain.model.Question;
import ru.otus.domain.model.Test;
import ru.otus.domain.service.FrontendService;
import ru.otus.domain.service.QuestionService;
import ru.otus.domain.service.TestService;

import java.util.Locale;

@Service
public class FrontendServiceImplementation implements FrontendService {
	private final ConsoleUtility consoleUtility;
	private final MessageSource messageSource;
	private final Locale locale;
	private final QuestionService questionService;
	private final TestService testService;

	public FrontendServiceImplementation(
			ConsoleUtility consoleUtility,
			MessageSource messageSource,
			ApplicationProperties applicationProperties,
			QuestionService questionService,
			TestService testService
	) {
		this.consoleUtility = consoleUtility;
		this.messageSource = messageSource;
		this.locale = new Locale(applicationProperties.getLocale());
		this.questionService = questionService;
		this.testService = testService;
	}

	@Override
	public String getFirstName() {
		System.out.print(messageSource.getMessage("enter.name", null, locale) + ": ");
		return consoleUtility.getUserInput();
	}

	@Override
	public String getLastName() {
		System.out.print(messageSource.getMessage("enter.surname", null, locale) + ": ");
		return consoleUtility.getUserInput();
	}

	@Override
	public void greeting() {
		System.out.println(messageSource.getMessage("greeting", null, locale));
	}

	@Override
	public String getAnswer(Question question) {
		System.out.println(questionService.stringifyQuestion(question));
		return consoleUtility.getUserInput();
	}

	@Override
	public void printResult(Test test) {
		String result = messageSource.getMessage("test.passed.by.student", null, locale) + ": " +
				test.getLastName() +
				" " +
				test.getFirstName() +
				"\n" +
				messageSource.getMessage("percentage.of.correct.answers", null, locale) + ": " +
				testService.getSuccessPercentage(test) +
				"%";

		System.out.println(result);
	}
}
