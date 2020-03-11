package ru.otus.repository;

import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

	List<Genre> findAll();

	Optional<Genre> findByName(String name);

	Genre updateName(Genre genre, String newName);
}
