package ru.otus.domain.service.dao;

import ru.otus.domain.model.Author;

import java.util.List;

public interface AuthorDao {
	List<Author> findAll();

	Author findById(Long id);

	Author findByName(String name);

	List<Author> findByBookId(Long id);

	List<Author> findAllUsed();

	Long insert(Author author);

	int update(Author author);

	int deleteById(Long id);
}
