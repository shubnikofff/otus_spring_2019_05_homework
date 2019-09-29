package ru.otus.domain.service;

import ru.otus.domain.model.Book;

import java.util.List;

public interface BookDao {

	Book getById(int id);

	int insert(Book book);

	List<Book> getAll();

	void delete(Book book);

	void update(Book book);
}
