package ru.otus.bookregistry.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.bookregistry.model.Book;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {

	Optional<Book> findByOwner(String owner);
}
