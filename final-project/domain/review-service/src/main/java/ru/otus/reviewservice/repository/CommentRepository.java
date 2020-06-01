package ru.otus.reviewservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.reviewservice.model.Comment;

import java.util.Collection;

public interface CommentRepository extends MongoRepository<Comment, String> {

	Collection<Comment> findByBookId(String bookId);

	void deleteByBookId(String bookId);
}
