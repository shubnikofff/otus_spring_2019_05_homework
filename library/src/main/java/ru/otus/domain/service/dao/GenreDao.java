package ru.otus.domain.service.dao;

import ru.otus.domain.model.Genre;

import java.util.List;

public interface GenreDao {

	List<Genre> findAll();

	Genre findByById(Long id);

	Genre findByName(String name);

	int save(Genre genre);

	int deleteById(Long id);
}
