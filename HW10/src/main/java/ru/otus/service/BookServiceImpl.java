package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;
import ru.otus.exception.BookNotFound;
import ru.otus.repository.BookRepository;
import ru.otus.request.SaveBookRequest;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookRepository repository;

	@Override
	public List<Book> getAll() {
		return repository.findAll();
	}

	@Override
	public Book getOne(String id) {
		return repository.findById(id).orElseThrow(BookNotFound::new);
	}

	@Override
	public void create(SaveBookRequest request) {
		final Book book = new Book(
				request.getTitle(),
				new Genre(request.getGenre()),
				request.getAuthors().stream().map(Author::new).collect(toList())
		);
		repository.save(book);
	}

	@Override
	public void update(String id, SaveBookRequest request) {
		final Book book = repository.findById(id).orElseThrow(BookNotFound::new);
		book.setTitle(request.getTitle());
		book.setGenre(new Genre(request.getGenre()));
		book.setAuthors(request.getAuthors().stream().map(Author::new).collect(toList()));
		repository.save(book);
	}

	@Override
	public void delete(String id) {
		final Book book = repository.findById(id).orElseThrow(BookNotFound::new);
		repository.delete(book);
	}
}
