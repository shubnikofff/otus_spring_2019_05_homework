package ru.otus.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

	@Override
	List<Genre> findAll();

	Optional<Genre> findByName(String name);

	@Override
	<S extends Genre> S save(S entity);

	@Override
	void deleteById(Long id);
}
