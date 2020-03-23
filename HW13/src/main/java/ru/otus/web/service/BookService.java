package ru.otus.web.service;

import org.springframework.security.access.annotation.Secured;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.web.request.SaveBookRequest;

import java.util.List;
import java.util.Optional;

public interface BookService {

	List<Book> getAllBooks();

	Optional<Book> getBook(String id);

	List<Comment> getBookComments(Book book);

	@Secured("ROLE_ADMIN")
	Book createBook(SaveBookRequest request);

	@Secured("ROLE_ADMIN")
	Book updateBook(Book book, SaveBookRequest request);

	@Secured("ROLE_ADMIN")
	void deleteBook(Book book);
}
