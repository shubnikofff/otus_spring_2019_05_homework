package ru.otus.service;

import ru.otus.domain.model.Comment;
import ru.otus.exception.BookNotFound;
import ru.otus.exception.CommentNotFound;
import ru.otus.request.CommentRequest;

import java.util.List;

public interface CommentService {

	List<Comment> getAll(String bookId);

	void create(CommentRequest request) throws BookNotFound;

	void update(String id, CommentRequest request) throws CommentNotFound, BookNotFound;

	void delete(String id) throws CommentNotFound;
}
