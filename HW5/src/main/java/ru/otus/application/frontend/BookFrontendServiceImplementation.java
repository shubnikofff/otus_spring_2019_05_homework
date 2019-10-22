package ru.otus.application.frontend;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.otus.domain.exception.OperationException;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;
import ru.otus.domain.model.Options;
import ru.otus.domain.service.IOService;
import ru.otus.domain.service.Stringifier;
import ru.otus.domain.service.dao.BookDao;
import ru.otus.domain.service.frontend.BookFrontendService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookFrontendServiceImplementation implements BookFrontendService {
	private final BookDao dao;
	private final IOService io;
	private final Stringifier<Book> stringifier;

	public BookFrontendServiceImplementation(BookDao dao, IOService io, Stringifier<Book> stringifier) {
		this.dao = dao;
		this.io = io;
		this.stringifier = stringifier;
	}

	@Override
	public String printAll() {
		final List<Book> bookList = dao.findAll();
		return stringifier.stringify(bookList);
	}

	@Override
	public Book chooseBook() {
		final Options<Book> options = new Options<>(dao.findAll());
		final String message = "Choose book from the list:\n" + stringifier.stringify(options);
		return io.getOneOf(options, message);
	}

	@Override
	public void create(String title, String genreName, List<String> authorNames) throws OperationException {
		final List<Author> authors = authorNames.stream().map(name -> new Author(null, name)).collect(Collectors.toList());
		try {
			dao.insert(new Book(null, title, authors, new Genre(null, genreName)));
		} catch (DuplicateKeyException e) {
			throw new OperationException("Book with that title already exists", e);
		}
	}

	@Override
	public void update(Book book, String title, String genreName, List<String> authorNames) throws OperationException {
		final Genre genre = book.getGenre().getName().equals(genreName) ? book.getGenre() : new Genre(null, genreName);
		final Map<String, Author> currentAuthors = book.getAuthors().stream().collect(Collectors.toMap(Author::getName, a -> a));
		final List<Author> authors = authorNames.stream()
				.map(name -> currentAuthors.containsKey(name) ? currentAuthors.get(name) : new Author(null, name))
				.collect(Collectors.toList());
		try {
			dao.update(new Book(book.getId(), title, authors, genre));
		} catch (DuplicateKeyException e) {
			throw new OperationException("Book with that title already exists", e);
		}
	}

	@Override
	public void delete(Book book) {
		dao.deleteById(book.getId());
	}
}
