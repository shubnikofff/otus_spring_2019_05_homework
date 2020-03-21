package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.access.annotation.Secured;
import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {

	Optional<Book> findByTitle(String title);

	List<Book> findByGenreName(String name);

	@Override
	@Secured("ROLE_ADMIN")
	@SuppressWarnings("unchecked")
	Book save(Book book);
}
