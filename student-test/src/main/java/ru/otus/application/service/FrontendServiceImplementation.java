package ru.otus.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.domain.model.Question;
import ru.otus.domain.model.Test;
import ru.otus.domain.service.FrontendService;

import java.io.InputStream;
import java.util.Locale;
import java.util.Scanner;

@Service
public class FrontendServiceImplementation implements FrontendService {
	private final Scanner scanner;
	private final MessageSource messageSource;
	private final Locale locale;

	@Autowired
	public FrontendServiceImplementation(InputStream inputStream, MessageSource messageSource, @Value("${locale}") String locale) {
		scanner = new Scanner(inputStream);
		this.messageSource = messageSource;
		this.locale = new Locale(locale);
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
		System.out.println(question);
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
				test.getSuccessPercentage() +
				"%";

		System.out.println(result);
	}
}
