package ru.otus.web.service;

import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.web.request.SaveBookRequest;

import java.util.List;
import java.util.Optional;

public interface BookService {

	List<Book> getAllBooks();

	Optional<Book> getBook(String id);

	List<Comment> getBookComments(Book book);

	Book createBook(SaveBookRequest request);

	Book updateBook(Book book, SaveBookRequest request);

	void deleteBook(Book book);
}
