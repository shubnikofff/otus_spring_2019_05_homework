package ru.otus.domain.repository;

import ru.otus.domain.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
	List<Genre> findAll();

	Optional<Genre> findByName(String name);

	Genre save(Genre genre);

	void remove(Genre genre);
}
