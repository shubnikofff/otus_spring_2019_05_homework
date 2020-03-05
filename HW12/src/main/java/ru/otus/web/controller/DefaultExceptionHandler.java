package ru.otus.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.web.exception.AuthorNotFound;
import ru.otus.web.exception.BookNotFound;

@ControllerAdvice
public class DefaultExceptionHandler {

	@ExceptionHandler(BookNotFound.class)
	ModelAndView handleBookNotFound() {
		return new ModelAndView("book/not-found", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AuthorNotFound.class)
	ModelAndView handleAuthorNotFound() {
		return new ModelAndView("author/not-found", HttpStatus.NOT_FOUND);
	}
}
