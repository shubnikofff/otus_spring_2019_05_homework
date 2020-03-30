package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.model.CommentDocumentModel;

public interface CommentMongoRepository extends MongoRepository<CommentDocumentModel, String> {
}
