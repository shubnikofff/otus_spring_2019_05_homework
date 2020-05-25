package ru.otus.bookregistry.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.bookregistry.dto.BookDto;
import ru.otus.bookregistry.exception.BookNotFoundException;
import ru.otus.bookregistry.model.Author;
import ru.otus.bookregistry.model.Book;
import ru.otus.bookregistry.model.Genre;
import ru.otus.bookregistry.repository.BookRepository;

import java.util.Collection;
import java.util.Collections;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;

	@HystrixCommand(commandKey = "getAllBooks", fallbackMethod = "getAllFallback")
	@Override
	public Collection<BookDto> getAll(String username) {
		return bookRepository.findAll().stream()
				.map(book -> BookTransformer.toBookDto(book, username))
				.collect(toList());
	}

	private Collection<BookDto> getAllFallback(String username) {
		return Collections.emptyList();
	}

	@HystrixCommand(commandKey = "getOneBook", fallbackMethod = "getOneFallback", ignoreExceptions = {BookNotFoundException.class})
	@Override
	public BookDto getOne(String id, String username) {
		return bookRepository.findById(id)
				.map(book -> BookTransformer.toBookDto(book, username))
				.orElseThrow(BookNotFoundException::new);
	}

	@HystrixCommand(commandKey = "getOwnBooks", fallbackMethod = "getOwnFallback")
	@Override
	public Collection<BookDto> getOwnBooks(String owner) {
		return bookRepository.findByOwner(owner).stream()
				.map(book -> BookTransformer.toBookDto(book, owner))
				.collect(toList());
	}

	private Collection<BookDto> getOwnFallback(String username) {
		return Collections.emptyList();
	}

	private BookDto getOneFallback(String id, String username) {
		return new BookDto(id, "Not available", "Not available", Collections.emptyList(), false);
	}

	@HystrixCommand(commandKey = "exists", fallbackMethod = "existsFallback")
	@Override
	public boolean exists(String id) {
		return bookRepository.existsById(id);
	}

	boolean existsFallback(String id) {
		return false;
	}

	@HystrixCommand(commandKey = "createBook", fallbackMethod = "createBookFallback")
	@Override
	public String create(BookDto bookDto, String username) {
		return bookRepository.save(BookTransformer.toBook(bookDto, username)).getId();
	}

	private String createBookFallback(BookDto bookDto, String username) {
		return null;
	}

	@HystrixCommand(commandKey = "updateBook", fallbackMethod = "updateFallback", ignoreExceptions = {BookNotFoundException.class})
	@Override
	public void update(String id, BookDto bookDto) {
		final Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
		book.setTitle(bookDto.getTitle());
		book.setGenre(new Genre(bookDto.getGenre()));
		book.setAuthors(bookDto.getAuthors().stream().map(Author::new).collect(toList()));
		bookRepository.save(book);
	}

	private void updateFallback(String id, BookDto bookDto) {
	}

	@HystrixCommand(commandKey = "deleteBook", fallbackMethod = "deleteFallback", ignoreExceptions = {BookNotFoundException.class})
	@Override
	public void delete(String id) {
		final Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
		bookRepository.delete(book);
	}

	private void deleteFallback(String id) {
	}
}
