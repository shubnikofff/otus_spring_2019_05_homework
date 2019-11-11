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
import ru.otus.domain.repository.CommentRepository;
import ru.otus.domain.repository.GenreRepository;
import ru.otus.domain.service.IOService;
import ru.otus.domain.service.Options;
import ru.otus.domain.service.Stringifier;
import ru.otus.domain.service.frontend.BookFrontend;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookFrontendImplementation implements BookFrontend {
	private final AuthorRepository authorRepository;
	private final BookRepository bookRepository;
	private final CommentRepository commentRepository;
	private final GenreRepository genreRepository;
	private final IOService io;
	private final Stringifier<Book> bookStringifier;
	private final Stringifier<Comment> commentStringifier;

	@Override
	@Transactional
	public String printAll() {
		return bookStringifier.stringify(bookRepository.findAll());
	}

	@Override
	@Transactional
	public String printComments(Book book) {
		return commentStringifier.stringify(commentRepository.findAllByBookId(book.getId()));
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
		final Book book = new Book(null, title, getGenreByName(genreName), getAuthorsByNames(authorNames));

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
			commentRepository.deleteByBookId(book.getId());
			bookRepository.deleteById(book.getId());
		} catch (DataIntegrityViolationException e) {
			throw new OperationException("Cannot delete book", e);
		}
	}

	@Override
	@Transactional
	public void addComment(Book book, String name, String text) {
		bookRepository.findById(book.getId()).ifPresent(b -> commentRepository.save(new Comment(null, name, text, b)));
	}

	private List<Author> getAuthorsByNames(List<String> names) {
		final Map<String, Author> existingAuthors = authorRepository.findByNames(names).stream()
				.collect(Collectors.toMap(Author::getName, Function.identity()));

		return names.stream()
				.map(name -> existingAuthors.containsKey(name) ? existingAuthors.get(name) : new Author(null, name))
				.collect(Collectors.toList());
	}

	private Genre getGenreByName(String name) {
		return genreRepository.findByName(name).orElse(new Genre(null, name));
	}
}
