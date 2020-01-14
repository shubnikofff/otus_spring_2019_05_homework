package ru.otus.application.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.model.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {

	Book findByTitle(String title);

	List<Book> findByGenreName(String name);
}
