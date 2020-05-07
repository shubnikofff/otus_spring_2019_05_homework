package ru.otus.bookregistry.service;


import ru.otus.bookregistry.dto.BookDto;

import java.util.Collection;

public interface BookService {

	Collection<BookDto> getAll();

	BookDto getOne(String id);

	String create(BookDto bookDto);

	void update(String id, BookDto bookDto);

	void delete(String id);
}
