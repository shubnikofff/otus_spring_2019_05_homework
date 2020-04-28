package ru.otus.web.service;

import ru.otus.domain.Book;
import ru.otus.web.request.SaveBookRequest;

import java.util.List;

public interface BookService {

	List<Book> getAll();

	Book getOne(String id);

	void create(SaveBookRequest request);

	void update(String id, SaveBookRequest request);

	void delete(String id);
}
