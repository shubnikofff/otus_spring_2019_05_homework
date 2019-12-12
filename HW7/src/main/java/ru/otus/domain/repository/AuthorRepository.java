package ru.otus.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.model.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

	@Override
	List<Author> findAll();

	List<Author> findByNameIn(List<String> names);

	@Override
	<S extends Author> S save(S entity);

	@Override
	void deleteById(Long id);
}
