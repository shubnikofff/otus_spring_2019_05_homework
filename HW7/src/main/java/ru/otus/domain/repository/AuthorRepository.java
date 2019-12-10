package ru.otus.domain.repository;

import ru.otus.domain.model.Author;

import java.util.List;

public interface AuthorRepository {
	List<Author> findAll();

	List<Author> findByNames(List<String> names);

	Author save(Author author);

	void deleteById(Long id);
}
