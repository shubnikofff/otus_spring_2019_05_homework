package ru.otus.domain.repository;

import ru.otus.domain.model.Comment;

import java.util.List;

public interface CommentRepository {
	List<Comment> findAll(Long bookId);

	Comment save(Comment comment);
}
