package ru.otus.service;

import ru.otus.domain.model.Book;
import ru.otus.request.SaveBookRequest;

import java.util.List;

public interface BookService {

	List<Book> getAll();

	Book getOne(String id);

	void create(SaveBookRequest request);

	void update(String id, SaveBookRequest request);

	void delete(String id);
}
