package ru.otus.domain.exception;

public class OperationException extends Exception {
	public OperationException(String message, Throwable cause) {
		super(message, cause);
	}
}
