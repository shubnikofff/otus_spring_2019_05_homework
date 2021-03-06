package ru.otus.bookregistry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such genre")
public class GenreNotFoundException extends RuntimeException {
}
