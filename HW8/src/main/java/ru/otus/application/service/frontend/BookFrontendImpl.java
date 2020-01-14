package ru.otus.application.service.frontend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.application.repository.BookRepository;
import ru.otus.application.repository.CommentRepository;
import ru.otus.domain.exception.OperationException;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;
import ru.otus.domain.model.Genre;
import ru.otus.domain.service.IOService;
import ru.otus.domain.service.Options;
import ru.otus.domain.service.Stringifier;
import ru.otus.domain.service.frontend.BookFrontend;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookFrontendImpl implements BookFrontend {

	private final BookRepository bookRepository;
	private final CommentRepository commentRepository;
	private final IOService io;
	private final Stringifier<Book> bookStringifier;
	private final Stringifier<Comment> commentStringifier;

	@Override
	public String printAll() {
		return bookStringifier.stringify(bookRepository.findAll());
	}

	@Override
	public String printComments(Book book) {
		return commentStringifier.stringify(commentRepository.findByBookId(book.getId()));
	}

	@Override
	public Book chooseOne() {
		final Options<Book> options = new Options<>(bookRepository.findAll());
		final String message = "Choose book from the list:\n" + bookStringifier.stringify(options);
		return io.getOneOf(options, message);
	}

	@Override
	public void create(String title, String genreName, List<String> authorNames) throws OperationException {
		if (bookRepository.findByTitle(title) != null) {
			throw new OperationException("Book with that name already exists");
		}

		final Author[] authors = authorNames.stream().map(Author::new).toArray(Author[]::new);
		bookRepository.save(new Book(title, new Genre(genreName), authors));
	}

	@Override
	public void update(Book book, String title, String genreName, List<String> authorNames) throws OperationException {
		if (!book.getTitle().equals(title) && bookRepository.findByTitle(title) != null) {
			throw new OperationException("Book with that name already exists");
		}

		book.setTitle(title);
		book.setGenre(new Genre(genreName));
		book.setAuthors(authorNames.stream().map(Author::new).collect(Collectors.toList()));
		bookRepository.save(book);
	}

	@Override
	public void delete(Book book) {
		bookRepository.delete(book);
	}

	@Override
	public void addComment(Book book, String user, String text) {
		bookRepository.findById(book.getId()).ifPresent(b -> commentRepository.save(new Comment(user, text, b)));
	}
}
