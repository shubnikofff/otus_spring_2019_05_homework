package ru.otus.application.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.model.Question;
import ru.otus.domain.model.Test;
import ru.otus.domain.service.FrontendService;

import java.util.Scanner;

@Service
public class FrontendServiceImplementation implements FrontendService {
	private Scanner scanner = new Scanner(System.in);

	@Override
	public String getFirstName() {
		System.out.print("Введите имя: ");
		return scanner.nextLine();
	}

	@Override
	public String getLastName() {
		System.out.print("Введите фамилию: ");
		return scanner.nextLine();
	}

	@Override
	public String getAnswer(Question question) {
		System.out.println(question);
		return scanner.nextLine();
	}

	@Override
	public void printResult(Test test) {
		String result = "Тест пройден студентом: " +
				test.getLastName() +
				" " +
				test.getFirstName() +
				"\n" +
				"Процент верных ответов: " +
				test.getSuccessPercentage() +
				"%";

		System.out.println(result);
	}
}
