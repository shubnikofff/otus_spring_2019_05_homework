package ru.otus.service;

import ru.otus.domain.model.Comment;
import ru.otus.exception.CommentNotFound;
import ru.otus.request.CreateCommentRequest;
import ru.otus.request.UpdateCommentRequest;

import java.util.List;

public interface CommentService {

	List<Comment> getAll(String bookId);

	void create(CreateCommentRequest request);

	void update(String id, UpdateCommentRequest request);

	void delete(String id) throws CommentNotFound;
}
