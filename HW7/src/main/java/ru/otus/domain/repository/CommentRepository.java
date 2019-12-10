package ru.otus.domain.repository;

import ru.otus.domain.model.Comment;

import java.util.List;

public interface CommentRepository {
	List<Comment> findAllByBookId(Long bookId);

	Comment save(Comment comment);

	void deleteByBookId(Long bookId);
}
