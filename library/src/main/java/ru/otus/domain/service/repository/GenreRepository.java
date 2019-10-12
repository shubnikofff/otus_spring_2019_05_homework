package ru.otus.domain.service.repository;

import ru.otus.domain.model.Genre;

import java.util.List;

public interface GenreRepository {

	List<Genre> getAll();

	Genre getById(Long id);

	int save(Genre genre);

	int deleteById(Long id);
}
