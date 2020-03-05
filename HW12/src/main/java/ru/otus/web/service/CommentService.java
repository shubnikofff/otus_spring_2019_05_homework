package ru.otus.web.service;

import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;
import ru.otus.web.request.CreateCommentRequest;

import java.util.Optional;

public interface CommentService {

	Optional<Book> getBook(String id);

	Comment createComment(Book book, CreateCommentRequest request);
}
