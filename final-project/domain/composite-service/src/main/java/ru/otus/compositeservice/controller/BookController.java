package ru.otus.compositeservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
		return new ResponseEntity<>(bookService.getAllBooks(username), HttpStatus.OK);
	}

	@GetMapping("/book/{id}")
	public ResponseEntity<BookCompleteDataDto> getBook(@PathVariable("id") String id, @RequestHeader("username") String username) {
		return new ResponseEntity<>(bookService.getBookCompleteData(id, username), HttpStatus.OK);
	}

	@DeleteMapping("/book/{id}")
	public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") String id) {
		bookService.deleteBook(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
