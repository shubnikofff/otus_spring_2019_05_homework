package ru.otus.domain.repository;

import ru.otus.domain.model.Author;

import java.util.List;

public interface AuthorRepository {
	List<Author> findAll();

	Author save(Author author);

	void remove(Author author);
}
