package ru.otus.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.model.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

	Optional<Genre> findByName(String name);
}
