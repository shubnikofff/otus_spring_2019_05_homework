package ru.otus.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.model.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
	List<Book> findByGenreName(String name);
}
