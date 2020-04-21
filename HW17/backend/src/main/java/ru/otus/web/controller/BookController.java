package ru.otus.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.Book;
import ru.otus.web.request.SaveBookRequest;
import ru.otus.web.service.BookService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController {

	private final BookService service;

	@GetMapping("/book")
	ResponseEntity<List<Book>> getAllBooks() {
		final List<Book> books = service.getAll();
		return new ResponseEntity<>(books, HttpStatus.OK);
	}

	@GetMapping("/book/{id}")
	ResponseEntity<Book> getBookById(@PathVariable("id") String id) {
		final Book book = service.getOne(id);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

	@PostMapping("/book")
	ResponseEntity<HttpStatus> createBook(@RequestBody SaveBookRequest request) {
		service.create(request);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/book/{id}")
	ResponseEntity<HttpStatus> updateBook(@PathVariable("id") String id, @RequestBody SaveBookRequest request) {
		service.update(id, request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/book/{id}")
	ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") String id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
