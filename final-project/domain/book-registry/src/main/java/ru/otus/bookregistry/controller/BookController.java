package ru.otus.bookregistry.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.bookregistry.dto.BookDto;
import ru.otus.bookregistry.service.BookService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;

	@GetMapping("/")
	public ResponseEntity<Collection<BookDto>> getAllBooks(@RequestHeader("username") String username) {
		return new ResponseEntity<>(bookService.getAll(username), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookDto> getBook(@PathVariable("id") String id, @RequestHeader("username") String username) {
		return new ResponseEntity<>(bookService.getOne(id, username), HttpStatus.OK);
	}

	@GetMapping("/own")
	public ResponseEntity<Collection<BookDto>> getOwnBooks(@RequestHeader("username") String username) {
		return new ResponseEntity<>(bookService.getOwnBooks(username), HttpStatus.OK);
	}

	@PostMapping("/")
	ResponseEntity<String> createBook(@RequestBody BookDto bookDto, @RequestHeader("username") String username) {
		return new ResponseEntity<>(bookService.create(bookDto, username), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<HttpStatus> updateBook(@PathVariable("id") String id, @RequestBody BookDto bookDto) {
		bookService.update(id, bookDto);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{id}")
	ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") String id) {
		bookService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
