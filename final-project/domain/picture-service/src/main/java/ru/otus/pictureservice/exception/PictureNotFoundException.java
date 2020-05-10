package ru.otus.pictureservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such picture")
public class PictureNotFoundException extends RuntimeException {
}
