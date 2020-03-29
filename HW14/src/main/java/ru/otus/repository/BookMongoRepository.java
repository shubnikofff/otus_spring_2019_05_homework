package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.model.BookDocumentModel;

public interface BookMongoRepository extends MongoRepository<BookDocumentModel, String> {
}
