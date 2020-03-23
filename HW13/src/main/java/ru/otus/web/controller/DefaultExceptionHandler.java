package ru.otus.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.web.exception.AuthorNotFound;
import ru.otus.web.exception.BookNotFound;
import ru.otus.web.exception.CommentNotFound;
import ru.otus.web.exception.GenreNotFound;

@ControllerAdvice
public class DefaultExceptionHandler {

	@ExceptionHandler(AuthorNotFound.class)
	public ModelAndView handleAuthorNotFound() {
		return new ModelAndView("author/not-found", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BookNotFound.class)
	public ModelAndView handleBookNotFound() {
		return new ModelAndView("book/not-found", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CommentNotFound.class)
	public ModelAndView handleCommentNotFound() {
		return new ModelAndView("comment/not-found", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(GenreNotFound.class)
	public ModelAndView handleGenreNotFound() {
		return new ModelAndView("genre/not-found", HttpStatus.NOT_FOUND);
	}
}
