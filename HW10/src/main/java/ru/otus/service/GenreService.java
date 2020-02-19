package ru.otus.service;

import ru.otus.domain.model.Genre;
import ru.otus.request.UpdateGenreRequest;

import java.util.List;

public interface GenreService {

	List<Genre> getAll();

	void update(String name, UpdateGenreRequest request);
}
