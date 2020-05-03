package ru.otus.utilityservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.utilityservice.model.Book;

public interface BookRepository extends MongoRepository<Book, String> {

	Book findByTitle(String title);
}
