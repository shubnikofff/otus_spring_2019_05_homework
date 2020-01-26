package ru.otus.repository;

import ru.otus.domain.model.Author;

import java.util.List;

public interface AuthorRepository {

	List<Author> findAll();

	void updateName(Author author, String newName);
}
