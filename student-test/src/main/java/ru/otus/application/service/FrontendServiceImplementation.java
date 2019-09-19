package ru.otus.application.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.application.configuration.ApplicationProperties;
import ru.otus.domain.model.Question;
import ru.otus.domain.service.FrontendService;

import java.io.InputStream;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

@Service
public class FrontendServiceImplementation implements FrontendService {
	private final Scanner scanner;
	private final MessageSource messageSource;
	private final Locale locale;

	public FrontendServiceImplementation(
			InputStream inputStream,
			MessageSource messageSource,
			ApplicationProperties applicationProperties
	) {
		this.scanner = new Scanner(inputStream);
		this.messageSource = messageSource;
		this.locale = new Locale(applicationProperties.getLocale());
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
	public void greeting() {
		System.out.println(messageSource.getMessage("greeting", null, locale));
	}

	@Override
	public String getAnswer(Question question) {
		String answer;

		do {
			System.out.println(question);
			answer = scanner.nextLine();
		} while (!Interview.isAnswerValid(answer, question));

		return answer;
	}

	@Override
	public void printResult(String name, Map<Question, String> answerMap) {
		final String result = messageSource.getMessage("test.passed.by.student", null, locale) + ": " +
				name + "\n" +
				messageSource.getMessage("percentage.of.correct.answers", null, locale) + ": " +
				Interview.getSuccessPercentage(answerMap) + "%";

		System.out.println(result);
	}

	private static class Interview {
		static boolean isAnswerValid(String answer, Question question) {
			if (question.getAnswers().isEmpty()) {
				return true;
			}

			return question.getAnswers().containsKey(answer);
		}

		static float getSuccessPercentage(Map<Question, String> answers) {
			float numberOfCorrectAnswers = 0;
			for (Map.Entry<Question, String> questionAnswerEntry : answers.entrySet()) {
				if (isAnswerCorrect(questionAnswerEntry.getKey(), questionAnswerEntry.getValue())) {
					numberOfCorrectAnswers++;
				}
			}

			return numberOfCorrectAnswers / answers.size() * 100;
		}

		private static boolean isAnswerCorrect(Question question, String answer) {
			return answer.toLowerCase().equals(question.getCorrectAnswer().toLowerCase());
		}
	}
}
