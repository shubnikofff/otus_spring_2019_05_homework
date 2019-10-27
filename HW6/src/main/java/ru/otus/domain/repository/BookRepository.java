package ru.otus.domain.repository;

import ru.otus.domain.model.Book;

import java.util.List;

public interface BookRepository {
	List<Book> findAll();

	Book save(Book book);

	void remove(Book book);
}
