package ru.otus.application.io;

import org.springframework.stereotype.Service;
import ru.otus.domain.service.IOService;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class SystemIOStreamService<E> implements IOService<E> {
	private final Scanner scanner = new Scanner(System.in);

	@Override
	public String getUserInput(String message) {
		System.out.print(message + ": ");
		return scanner.nextLine();
	}

	@Override
	public void print(String string) {
		System.out.println(string);
	}

	@Override
	public void printMap(Map<Long, E> map) {
		final String output = map.entrySet().stream().map(entry -> entry.getKey() + ". " + entry.getValue()).collect(Collectors.joining("\n"));
		print(output);
	}

	@Override
	public E getOneFromMap(Map<Long, E> map, String message) {
		print(message);

		Long id = null;

		do {
			printMap(map);
			try {
				id = scanner.nextLong();
			} catch (InputMismatchException e) {
				print("Only numbers allowed");
			}
			scanner.nextLine();
		} while (!map.containsKey(id));

		return map.get(id);
	}
}
