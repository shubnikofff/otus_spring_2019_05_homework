package ru.otus.bookregistry.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.bookregistry.model.Book;

import java.util.Collection;

public interface BookRepository extends MongoRepository<Book, String> {

	Collection<Book> findByOwner(String owner);
}
