package ru.otus.bookregistry.repository;

import ru.otus.bookregistry.model.Author;

import java.util.Collection;
import java.util.Optional;

public interface AuthorRepository {

	Collection<Author> findAll();

	Optional<Author> findById(String id);

	void updateName(Author author, String newName);
}
