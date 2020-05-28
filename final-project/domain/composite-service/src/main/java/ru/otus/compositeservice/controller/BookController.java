package ru.otus.compositeservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.compositeservice.dto.AllBooksItemDto;
import ru.otus.compositeservice.dto.BookCompleteDataDto;
import ru.otus.compositeservice.service.BookService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;

	@GetMapping("/book")
	public ResponseEntity<Collection<AllBooksItemDto>> getAllBook(@RequestHeader("username") String username) {
		return ResponseEntity.ok(bookService.getAllBooks(username));
	}

	@GetMapping("/book/{id}")
	public ResponseEntity<BookCompleteDataDto> getBook(@PathVariable("id") String id, @RequestHeader("username") String username) {
		return ResponseEntity.ok(bookService.getBookCompleteData(id, username));
	}

	@DeleteMapping("/book/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable("id") String id) {
		bookService.deleteBook(id);
		return ResponseEntity.noContent().build();
	}
}
