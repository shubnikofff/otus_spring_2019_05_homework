package ru.otus.domain.repository;

import ru.otus.domain.model.Genre;

import java.util.List;

public interface GenreRepository {
	List<Genre> findAll();

	Genre save(Genre genre);

	void remove(Genre genre);
}
