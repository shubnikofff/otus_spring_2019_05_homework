package ru.otus.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;
import ru.otus.repository.BookRepository;
import ru.otus.web.request.SaveBookRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookRepository repository;

	@Override
	public List<Book> getAllBooks() {
		return repository.findAll();
	}

	@Override
	public Optional<Book> getBook(String id) {
		return repository.findById(id);
	}

	@Override
	public Book createBook(SaveBookRequest request) {
		final Book book = new Book(
				request.getTitle(),
				new Genre(request.getGenre()),
				mapStringToAuthorList(request.getAuthors())
		);

		return repository.save(book);
	}

	@Override
	public Book updateBook(Book book, SaveBookRequest request) {
		book.setTitle(request.getTitle());
		book.setGenre(new Genre(request.getGenre()));
		book.setAuthors(mapStringToAuthorList(request.getAuthors()));

		return repository.save(book);
	}

	@Override
	public void deleteBook(Book book) {
		repository.delete(book);
	}

	private static List<Author> mapStringToAuthorList(String string) {
		return Arrays.stream(string.split("\\s*,\\s*")).map(Author::new).collect(toList());
	}
}
