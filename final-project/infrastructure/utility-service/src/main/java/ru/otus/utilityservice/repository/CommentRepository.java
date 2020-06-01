package ru.otus.utilityservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.utilityservice.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
