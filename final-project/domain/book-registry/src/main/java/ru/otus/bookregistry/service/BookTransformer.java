package ru.otus.bookregistry.service;

import ru.otus.bookregistry.dto.BookDto;
import ru.otus.bookregistry.model.Author;
import ru.otus.bookregistry.model.Book;
import ru.otus.bookregistry.model.Genre;

import static java.util.stream.Collectors.toList;

public class BookTransformer {

	public static BookDto toBookDto(Book book) {
		return new BookDto(
				book.getId(),
				book.getTitle(),
				book.getGenre().getName(),
				book.getAuthors().stream().map(Author::getName).collect(toList())
		);
	}

	public static Book toBook(BookDto bookDto) {
		return new Book(
				bookDto.getId(),
				bookDto.getTitle(),
				new Genre(bookDto.getGenre()),
				bookDto.getAuthors().stream().map(Author::new).collect(toList())
		);
	}
}
