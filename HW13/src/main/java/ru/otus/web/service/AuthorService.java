package ru.otus.web.service;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.web.request.UpdateAuthorRequest;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

	List<Author> getAllAuthors();

	Optional<Author> getAuthor(String name);

	List<Book> getAuthorBooks(Author author);

	Author updateAuthor(Author author, UpdateAuthorRequest request);
}
