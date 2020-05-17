package ru.otus.compositeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "Book registry unavailable")
public class BookRegistryUnavailableException extends RuntimeException {
}
