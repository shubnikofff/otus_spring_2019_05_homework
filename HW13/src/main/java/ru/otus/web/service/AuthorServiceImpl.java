package ru.otus.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.web.request.UpdateAuthorRequest;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

	private final AuthorRepository authorRepository;

	private final BookRepository bookRepository;

	@Override
	public List<Author> getAllAuthors() {
		return authorRepository.findAll();
	}

	@Override
	public Optional<Author> getAuthor(String name) {
		return authorRepository.findByName(name);
	}

	@Override
	public List<Book> getAuthorBooks(Author author) {
		return bookRepository.findByAuthorName(author.getName());
	}

	@Override
	public Author updateAuthor(Author author, UpdateAuthorRequest request) {
		return authorRepository.updateName(author, request.getName());
	}
}
