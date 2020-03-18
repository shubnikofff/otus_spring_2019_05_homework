package ru.otus.web.service;

import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.web.request.SaveCommentRequest;

import java.util.Optional;

public interface CommentService {

	Optional<Book> getBook(String id);

	Comment getComment(Long id);

	Comment createComment(Book book, SaveCommentRequest request);

	Comment updateComment(Long id, SaveCommentRequest request);
}
