package ru.otus.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;
import ru.otus.domain.model.Genre;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.web.request.SaveBookRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;

	private final CommentRepository commentRepository;

	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Optional<Book> getBook(String id) {
		return bookRepository.findById(id);
	}

	@Override
	public List<Comment> getBookComments(Book book) {
		return commentRepository.findByBookId(book.getId());
	}

	@Override
	public Book createBook(SaveBookRequest request) {
		final Book book = new Book(
				request.getTitle(),
				new Genre(request.getGenre()),
				mapStringToAuthorList(request.getAuthors())
		);

		return bookRepository.save(book);
	}

	@Override
	public Book updateBook(Book book, SaveBookRequest request) {
		book.setTitle(request.getTitle());
		book.setGenre(new Genre(request.getGenre()));
		book.setAuthors(mapStringToAuthorList(request.getAuthors()));

		return bookRepository.save(book);
	}

	@Override
	public void deleteBook(Book book) {
		bookRepository.delete(book);
	}

	private static List<Author> mapStringToAuthorList(String string) {
		return Arrays.stream(string.split("\\s*,\\s*")).map(Author::new).collect(toList());
	}
}
