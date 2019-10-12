package ru.otus.domain.service.dao;

import ru.otus.domain.model.Book;

import java.util.List;

public interface BookDao {

	List<Book> getAll();

	Book getById(Long id);

	int save(Book book);

	int deleteById(Long id);
}
