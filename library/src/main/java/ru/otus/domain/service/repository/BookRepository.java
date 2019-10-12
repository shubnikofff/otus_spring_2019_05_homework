package ru.otus.domain.service.repository;

import ru.otus.domain.model.Book;

import java.util.List;

public interface BookRepository {

	List<Book> getAll();

	Book getById(Long id);

	int save(Book book);

	int deleteById(Long id);
}
