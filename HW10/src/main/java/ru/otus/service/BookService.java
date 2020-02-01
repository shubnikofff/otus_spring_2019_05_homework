package ru.otus.service;

import ru.otus.domain.model.Book;
import ru.otus.request.BookRequest;

import java.util.List;

public interface BookService {

	List<Book> getAll();

	Book getOne(String id);

	void create(BookRequest request);

	void update(String id, BookRequest request);

	void delete(String id);
}
