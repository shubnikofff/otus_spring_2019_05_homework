package ru.otus.reviewservice.service;

import ru.otus.reviewservice.dto.CommentDto;

import java.util.Collection;

public interface CommentService {

	Collection<CommentDto> getAll(String bookId);

	String create(String bookId, CommentDto commentDto);

	void update(String id, CommentDto commentDto);

	void delete(String id);
}
