package ru.otus.application.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.service.IOService;

import java.util.Scanner;

@Service
public class SystemIOStreamService implements IOService {
	private final Scanner scanner = new Scanner(System.in);

	@Override
	public String getInput() {
		return scanner.nextLine();
	}

	@Override
	public void print(String string) {
		System.out.print(string);
	}
}
