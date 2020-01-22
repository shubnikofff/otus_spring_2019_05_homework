package ru.otus.controller;

import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;
import ru.otus.domain.model.Genre;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Mapper {
	static Book map(BookForm form) {
		return new Book(
				form.getTitle(),
				new Genre(form.getGenre()),
				getAuthorListFromString(form.getAuthors())
		);
	}

	static Book map(BookForm form, Book book) {
		book.setTitle(form.getTitle());
		book.setGenre(new Genre(form.getGenre()));
		book.setAuthors(getAuthorListFromString(form.getAuthors()));
		return book;
	}

	static BookForm map(Book book) {
		return new BookForm(
				book.getTitle(),
				book.getGenre().getName(),
				book.getAuthors().stream().map(Author::getName).collect(joining(", "))
		);
	}

	static Comment map(CommentForm form, Book book) {
		return new Comment(
				form.getUser(),
				form.getText(),
				book
		);
	}

	private static List<Author> getAuthorListFromString(String str) {
		return Arrays.stream(str.split("\\s*,\\s*")).map(Author::new).collect(toList());
	}
}
