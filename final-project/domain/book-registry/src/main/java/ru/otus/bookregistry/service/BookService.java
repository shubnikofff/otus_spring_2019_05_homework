package ru.otus.bookregistry.service;


import ru.otus.bookregistry.dto.BookDto;

import java.util.Collection;

public interface BookService {

	Collection<BookDto> getAll();

	BookDto getOne(String id);

	Collection<BookDto> getOwnBooks(String owner);

	boolean exists(String id);

	String create(BookDto bookDto, String username);

	void update(String id, BookDto bookDto);

	void delete(String id);
}
