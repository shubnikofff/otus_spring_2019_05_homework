package ru.otus.application.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.application.configuration.ApplicationProperties;
import ru.otus.domain.model.Question;
import ru.otus.domain.service.FrontendService;
import ru.otus.domain.service.IOService;

import java.util.Locale;
import java.util.Map;

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
		ioService.print(messageSource.getMessage("greeting", null, locale)  + "\n");
	}

	@Override
	public String getAnswer(Question question) {
		String answer;

		do {
			ioService.print(question.toString() + "\n");
			answer = ioService.getInput();
		} while (!Interview.isAnswerValid(answer, question));

		return answer;
	}

	@Override
	public void printResult(String name, Map<Question, String> answerMap) {
		final String result = messageSource.getMessage("test.passed.by.student", null, locale) + ": " +
				name + "\n" +
				messageSource.getMessage("percentage.of.correct.answers", null, locale) + ": " +
				Interview.getSuccessPercentage(answerMap) + "%\n";

		ioService.print(result);
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
