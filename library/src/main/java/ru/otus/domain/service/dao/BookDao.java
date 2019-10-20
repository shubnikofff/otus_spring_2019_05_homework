package ru.otus.domain.service.dao;

import ru.otus.domain.model.Book;

import java.util.List;

public interface BookDao {

	List<Book> getAll();

	Book getById(Long id);

	Long insert(Book book);

	int update(Book book);

	int deleteById(Long id);
}
