package ru.otus.application.service.io;

import org.springframework.stereotype.Service;
import ru.otus.domain.service.IOService;
import ru.otus.domain.service.Options;

import java.util.InputMismatchException;
import java.util.Scanner;

@Service
public class SystemIOStreamService implements IOService {
	private final Scanner scanner = new Scanner(System.in);

	@Override
	public <T> T getOneOf(Options<T> options, String message) {
		Integer input = null;

		do {
			print(message);
			try {
				input = scanner.nextInt();
			} catch (InputMismatchException e) {
				print("Only numbers allowed");
			}
			scanner.nextLine();
		} while (!options.contains(input));

		return options.get(input);
	}

	@Override
	public void print(String string) {
		System.out.println(string);
	}
}
