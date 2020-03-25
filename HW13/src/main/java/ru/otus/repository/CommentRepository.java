package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

	@PostFilter("hasPermission(filterObject, 'READ')")
	List<Comment> findByBookId(String bookId);

	@PostAuthorize("hasPermission(returnObject, 'READ')")
	Comment findCommentById(Long id);

	@Override
	@SuppressWarnings("unchecked")
	@PreAuthorize("hasPermission(#comment, 'WRITE')")
	Comment save(Comment comment);
}
