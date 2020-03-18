package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.access.prepost.PostFilter;
import ru.otus.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

	@PostFilter("hasPermission(filterObject, 'READ')")
	List<Comment> findByBookId(String bookId);
}
