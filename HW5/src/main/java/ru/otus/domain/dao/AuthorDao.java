package ru.otus.domain.dao;

import ru.otus.domain.model.Author;

import java.util.List;
import java.util.Set;

public interface AuthorDao {
	List<Author> findAll();

	List<Author> findAllByNameSet(Set<String> names);

	List<Long> findAllIdsByNameSet(Set<String> names);

	Author findById(Long id);

	Author findByName(String name);

	List<Author> findByBookId(Long id);

	List<Author> findAllUsed();

	Long insert(Author author);

	/**
	 * Inserts author objects from the given list
	 * @param authors objects for insert
	 * @return the number of rows affected
	 */
	int insertAll(List<Author> authors);

	int update(Author author);

	int deleteById(Long id);
}
