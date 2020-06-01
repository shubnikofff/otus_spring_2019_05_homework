package ru.otus.compositeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such exchange request")
public class ExchangeRequestNotFoundException extends RuntimeException {
}
