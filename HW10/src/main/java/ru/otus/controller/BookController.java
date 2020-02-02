package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.model.Book;
import ru.otus.request.SaveBookRequest;
import ru.otus.service.BookService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController {

	private final BookService service;

	@GetMapping("/books")
	ResponseEntity<List<Book>> getBooks() {
		final List<Book> books = service.getAll();
		return new ResponseEntity<>(books, HttpStatus.OK);
	}

	@GetMapping("/books/{id}")
	ResponseEntity<Book> getBook(@PathVariable("id") String id) {
		final Book book = service.getOne(id);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

	@PostMapping("/books")
	ResponseEntity<HttpStatus> create(@RequestBody SaveBookRequest request) {
		service.create(request);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/books/{id}")
	ResponseEntity<HttpStatus> update(@PathVariable("id") String id, @RequestBody SaveBookRequest request) {
		service.update(id, request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/books/{id}")
	ResponseEntity<HttpStatus> delete(@PathVariable("id") String id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
