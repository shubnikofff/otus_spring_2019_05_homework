package ru.otus.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

	@Override
	List<Book> findAll();

	@Override
	Optional<Book> findById(Long id);

	@Override
	<S extends Book> S save(S entity);

	@Override
	void deleteById(Long aLong);
}
