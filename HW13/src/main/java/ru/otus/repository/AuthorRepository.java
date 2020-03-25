package ru.otus.repository;

import org.springframework.security.access.annotation.Secured;
import ru.otus.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

	List<Author> findAll();

	Optional<Author> findByName(String name);

	@Secured("ROLE_ADMIN")
	Author updateName(Author author, String newName);
}
