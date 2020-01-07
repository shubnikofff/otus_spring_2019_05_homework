package ru.otus.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.model.Book;

public interface BookRepository extends MongoRepository<Book, String> {
}
