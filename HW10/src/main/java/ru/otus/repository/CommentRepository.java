package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.model.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

	List<Comment> findByBookId(String bookId);
}
