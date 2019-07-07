package ru.otus.application.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.application.configuration.ApplicationProperties;
import ru.otus.domain.model.Question;
import ru.otus.domain.model.Test;
import ru.otus.domain.service.FrontendService;
import ru.otus.domain.service.QuestionService;
import ru.otus.domain.service.TestService;

import java.io.InputStream;
import java.util.Locale;
import java.util.Scanner;

@Service
public class FrontendServiceImplementation implements FrontendService {
	private final Scanner scanner;
	private final MessageSource messageSource;
	private final Locale locale;
	private final QuestionService questionService;
	private final TestService testService;

	public FrontendServiceImplementation(
			InputStream inputStream,
			MessageSource messageSource,
			ApplicationProperties applicationProperties,
			QuestionService questionService,
			TestService testService
	) {
		scanner = new Scanner(inputStream);
		this.messageSource = messageSource;
		this.locale = new Locale(applicationProperties.getLocale());
		this.questionService = questionService;
		this.testService = testService;
	}

	@Override
	public String getFirstName() {
		System.out.print(messageSource.getMessage("enter.name", null, locale) + ": ");
		return scanner.nextLine();
	}

	@Override
	public String getLastName() {
		System.out.print(messageSource.getMessage("enter.surname", null, locale) + ": ");
		return scanner.nextLine();
	}

	@Override
	public String getAnswer(Question question) {
		System.out.println(questionService.stringifyQuestion(question));
		return scanner.nextLine();
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
