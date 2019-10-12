package ru.otus.domain.service.dao;

import ru.otus.domain.model.Genre;

import java.util.List;

public interface GenreDao {

	List<Genre> getAll();

	Genre getById(Long id);

	int save(Genre genre);

	int deleteById(Long id);
}
