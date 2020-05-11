package ru.otus.reviewservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such comment")
public class CommentNotFoundException extends RuntimeException {
}
