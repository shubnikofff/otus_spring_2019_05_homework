package ru.otus.bookregistry.service;


import ru.otus.bookregistry.dto.BookDto;

import java.util.Collection;
import java.util.Map;

public interface BookService {

	Collection<BookDto> getAll();

	BookDto getOne(String id);

	Map<String, BookDto> getByMultipleId(Collection<String> ids);

	Collection<BookDto> getOwnBooks(String owner);

	boolean exists(String id);

	String create(BookDto bookDto, String username);

	void update(String id, BookDto bookDto);

	void setOwner(Map<String, String> bookIdUsernameMap);

	void delete(String id);
}
