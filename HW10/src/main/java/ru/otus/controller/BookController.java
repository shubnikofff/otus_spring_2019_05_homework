package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.controller.dto.BookDto;
import ru.otus.domain.exception.NotFoundException;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;
import ru.otus.repository.BookRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController
public class BookController {

	private final BookRepository bookRepository;

	@GetMapping("/book")
	List<Book> getBooks() {
		return bookRepository.findAll();
	}

	@GetMapping("/book/{id}")
	Book getBook(@PathVariable("id") String id) {
		return bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book was not found"));
	}

	@PostMapping("/book")
	ResponseEntity<Book> create(@RequestBody BookDto dto) {
		final Book book = new Book(
				dto.getTitle(),
				new Genre(dto.getGenre()),
				dto.getAuthors().stream().map(Author::new).collect(toList())
		);

		bookRepository.save(book);
		return new ResponseEntity<>(book, HttpStatus.CREATED);
	}

	@PutMapping("/book/{id}")
	ResponseEntity<HttpStatus> update(@PathVariable("id") String id, @RequestBody BookDto dto) {
		final Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book was not found"));
		book.setTitle(dto.getTitle());
		book.setGenre(new Genre(dto.getGenre()));
		book.setAuthors(dto.getAuthors().stream().map(Author::new).collect(toList()));

		bookRepository.save(book);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/book/{id}")
	ResponseEntity<HttpStatus> delete(@PathVariable("id") String id) {
		final Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book was not found"));

		bookRepository.delete(book);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
