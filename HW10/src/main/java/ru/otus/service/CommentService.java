package ru.otus.service;

import ru.otus.exception.CommentNotFound;
import ru.otus.request.CreateCommentRequest;
import ru.otus.request.UpdateCommentRequest;
import ru.otus.response.CommentResponse;
import ru.otus.response.CreateCommentResponse;

import java.util.List;

public interface CommentService {

	List<CommentResponse> getAll(String bookId);

	CreateCommentResponse create(CreateCommentRequest request);

	void update(String id, UpdateCommentRequest request);

	void delete(String id) throws CommentNotFound;
}
