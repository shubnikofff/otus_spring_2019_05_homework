package ru.otus.web.service;

import ru.otus.domain.Genre;
import ru.otus.web.request.UpdateGenreRequest;

import java.util.List;

public interface GenreService {

	List<Genre> getAll();

	void update(String name, UpdateGenreRequest request);
}
