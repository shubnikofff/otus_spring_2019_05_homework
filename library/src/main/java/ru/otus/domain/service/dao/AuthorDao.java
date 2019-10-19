package ru.otus.domain.service.dao;

import ru.otus.domain.model.Author;

import java.util.List;
import java.util.Set;

public interface AuthorDao {
	List<Author> findAll();

	List<Author> findAllByNameSet(Set<String> names);

	Author findById(Long id);

	Author findByName(String name);

	List<Author> findByBookId(Long id);

	List<Author> findAllUsed();

	Long insert(Author author);

	int update(Author author);

	int deleteById(Long id);
}
