package ru.otus.application.service.frontend;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.otus.domain.exception.OperationException;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;
import ru.otus.domain.model.Genre;
import ru.otus.domain.repository.AuthorRepository;
import ru.otus.domain.repository.BookRepository;
import ru.otus.domain.repository.GenreRepository;
import ru.otus.domain.service.IOService;
import ru.otus.domain.service.Options;
import ru.otus.domain.service.Stringifier;
import ru.otus.domain.service.frontend.BookFrontend;

import javax.management.OperationsException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookFrontendImplementation implements BookFrontend {
	private final BookRepository bookRepository;
	private final GenreRepository genreRepository;
	private final AuthorRepository authorRepository;
	private final IOService io;
	private final Stringifier<Book> bookStringifier;
	private final Stringifier<Comment> commentStringifier;

	@Override
	public String printAll() {
		return bookStringifier.stringify(bookRepository.findAll());
	}

	@Override
	public String printComments(Book book) {
		final Optional<Book> searchResult = bookRepository.findById(book.getId());
		return searchResult.isPresent()
				? commentStringifier.stringify(searchResult.get().getComments())
				: "Book not found";
	}

	@Override
	public Book chooseOne() {
		final Options<Book> options = new Options<>(bookRepository.findAll());
		final String message = "Choose book from the list:\n" + bookStringifier.stringify(options);
		return io.getOneOf(options, message);
	}

	@Override
	@Transactional(rollbackOn = OperationException.class)
	public void create(String title, String genreName, List<String> authorNames) throws OperationException {
		final Book book = new Book(null, title, getGenreByName(genreName), getAuthorsByNames(authorNames), Collections.emptyList());

		try {
			bookRepository.save(book);
		} catch (DataIntegrityViolationException e) {
			throw new OperationException("Cannot create book", e);
		}
	}

	@Override
	@Transactional(rollbackOn = OperationException.class)
	public void update(Book book, String title, String genreName, List<String> authorNames) throws OperationException {
		book.setTitle(title);
		book.setGenre(getGenreByName(genreName));
		book.setAuthors(getAuthorsByNames(authorNames));

		try {
			bookRepository.save(book);
		} catch (DataIntegrityViolationException e) {
			throw new OperationException("Cannot update book", e);
		}
	}

	@Override
	@Transactional(rollbackOn = OperationException.class)
	public void delete(Book book) throws OperationException {
		try {
			bookRepository.remove(book);
		} catch (DataIntegrityViolationException e) {
			throw new OperationException("Cannot delete book", e);
		}
	}

	@Override
	@Transactional(rollbackOn = OperationException.class)
	public void addComment(Book book, String name, String text) throws OperationsException {
		final Book commentedBook = bookRepository.findById(book.getId()).orElseThrow(OperationsException::new);
		commentedBook.getComments().add(new Comment(null, name, text));
		bookRepository.save(commentedBook);
	}

	private List<Author> getAuthorsByNames(List<String> names) {
		final Map<String, Author> existingAuthors = authorRepository.findByNames(names).stream().collect(Collectors.toMap(Author::getName, a -> a));
		return names.stream()
				.map(name -> existingAuthors.containsKey(name) ? existingAuthors.get(name) : new Author(null, name))
				.collect(Collectors.toList());
	}

	private Genre getGenreByName(String name) {
		return genreRepository.findByName(name).orElse(new Genre(null, name));
	}
}
