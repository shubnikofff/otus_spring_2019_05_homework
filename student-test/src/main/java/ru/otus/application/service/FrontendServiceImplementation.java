package ru.otus.application.service;

import ru.otus.domain.Question;
import ru.otus.domain.Test;
import ru.otus.service.FrontendService;

import java.util.Scanner;

public class FrontendServiceImplementation implements FrontendService {
	@Override
	public String getFirstName() {
		System.out.print("Введите имя: ");
		return new Scanner(System.in).nextLine();
	}

	@Override
	public String getLastName() {
		System.out.print("Введите фамилию: ");
		return new Scanner(System.in).nextLine();
	}

	@Override
	public int getAnswer(Question question) {
		System.out.println(question);
		return new Scanner(System.in).nextInt();
	}

	@Override
	public void printResult(Test test) {
		System.out.println("Test is over");
	}
}
