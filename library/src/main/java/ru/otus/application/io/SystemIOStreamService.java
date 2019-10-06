package ru.otus.application.io;

import org.springframework.stereotype.Service;
import ru.otus.domain.service.IOService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Service
public class SystemIOStreamService implements IOService {
	private final Scanner scanner = new Scanner(System.in);

	@Override
	public Long getOneOf(List<Long> list, String message) {
		Long input = null;

		do {
			print(message);
			try {
				input = scanner.nextLong();
			} catch (InputMismatchException e) {
				print("Only numbers allowed");
			}
			scanner.nextLine();
		} while (!list.contains(input));

		return input;
	}

	@Override
	public void print(String string) {
		System.out.println(string);
	}
}
