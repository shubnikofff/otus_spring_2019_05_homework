package ru.otus.repository;

import org.springframework.security.access.annotation.Secured;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

	List<Genre> findAll();

	Optional<Genre> findByName(String name);

	@Secured("ROLE_ADMIN")
	Genre updateName(Genre genre, String newName);
}
