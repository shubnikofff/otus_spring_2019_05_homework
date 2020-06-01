package ru.otus.bookregistry.repository;


import ru.otus.bookregistry.model.Genre;

import java.util.Collection;
import java.util.Optional;

public interface GenreRepository {

	Collection<Genre> findAll();

	Optional<Genre> findByName(String name);

	void updateName(Genre genre, String newName);
}
