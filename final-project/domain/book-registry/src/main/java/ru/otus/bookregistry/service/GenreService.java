package ru.otus.bookregistry.service;

import ru.otus.bookregistry.dto.GenreDto;

import java.util.Collection;

public interface GenreService {

	Collection<GenreDto> getAll();

	void update(String id, GenreDto genreDto);
}
