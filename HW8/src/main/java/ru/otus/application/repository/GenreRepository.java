package ru.otus.application.repository;

import ru.otus.domain.model.Genre;

import java.util.List;

public interface GenreRepository {

	List<Genre> findAll();

	void updateName(Genre genre, String newName);
}
