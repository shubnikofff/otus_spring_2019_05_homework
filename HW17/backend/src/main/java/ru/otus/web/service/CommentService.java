package ru.otus.web.service;

import ru.otus.web.exception.CommentNotFound;
import ru.otus.web.request.CreateCommentRequest;
import ru.otus.web.request.UpdateCommentRequest;
import ru.otus.web.response.CommentResponse;
import ru.otus.web.response.CreateCommentResponse;

import java.util.List;

public interface CommentService {

	List<CommentResponse> getAll(String bookId);

	CreateCommentResponse create(CreateCommentRequest request);

	void update(String id, UpdateCommentRequest request);

	void delete(String id) throws CommentNotFound;
}
