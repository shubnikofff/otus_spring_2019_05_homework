package ru.otus.bookregistry.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.bookregistry.dto.BookDto;
import ru.otus.bookregistry.service.BookService;

import java.util.Collection;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;

	@GetMapping("/book")
	public ResponseEntity<Collection<BookDto>> getAllBooks() {
		return ResponseEntity.ok(bookService.getAll());
	}

	@GetMapping("/book/multiple/{ids}")
	public ResponseEntity<Map<String, BookDto>> getBooks(@PathVariable("ids") Collection<String> ids) {
		return ResponseEntity.ok(bookService.getByMultipleId(ids));
	}

	@GetMapping("/book/{id}")
	public ResponseEntity<BookDto> getBook(@PathVariable("id") String id) {
		return ResponseEntity.ok(bookService.getOne(id));
	}

	@GetMapping("/book/own")
	public ResponseEntity<Collection<BookDto>> getOwnBooks(@RequestHeader("username") String username) {
		return ResponseEntity.ok(bookService.getOwnBooks(username));
	}

	@PostMapping("/book/")
	ResponseEntity<String> createBook(@RequestBody BookDto bookDto, @RequestHeader("username") String username) {
		return new ResponseEntity<>(bookService.create(bookDto, username), HttpStatus.CREATED);
	}

	@PutMapping("/book/{id}")
	public ResponseEntity<?> updateBook(@PathVariable("id") String id, @RequestBody BookDto bookDto) {
		bookService.update(id, bookDto);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/book/owner")
	public ResponseEntity<?> setOwner(@RequestBody Map<String, String> bookIdUsernameMap) {
		bookService.setOwner(bookIdUsernameMap);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/book/{id}")
	ResponseEntity<?> deleteBook(@PathVariable("id") String id) {
		bookService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
