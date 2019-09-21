package ru.otus.application.utility;

import java.io.InputStream;
import java.util.Scanner;

public class ConsoleUtility {
	private Scanner scanner;

	public ConsoleUtility(InputStream inputStream) {
		this.scanner = new Scanner(inputStream);
	}

	public String getUserInput() {
		return scanner.nextLine();
	}
}
