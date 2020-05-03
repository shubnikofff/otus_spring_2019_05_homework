package ru.otus.bookregistry.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.bookregistry.model.Book;

public interface BookRepository extends MongoRepository<String, Book> {
}
