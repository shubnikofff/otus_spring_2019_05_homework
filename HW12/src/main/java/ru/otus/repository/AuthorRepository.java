package ru.otus.repository;

import ru.otus.domain.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

	List<Author> findAll();

	Optional<Author> findByName(String name);

	Author updateName(Author author, String newName);
}
