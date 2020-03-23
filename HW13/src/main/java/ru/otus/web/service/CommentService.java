package ru.otus.web.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.web.request.SaveCommentRequest;

import java.util.Optional;

public interface CommentService {

	Optional<Book> getBook(String id);

	@PostAuthorize("hasPermission(returnObject, 'WRITE')")
	Comment getComment(Long id);

	@Secured("ROLE_USER")
	Comment createComment(Book book, SaveCommentRequest request);

	@Secured("ROLE_USER")
	Comment updateComment(Long id, SaveCommentRequest request);
}
