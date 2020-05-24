package ru.otus.compositeservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.compositeservice.dto.BookCompleteDataDto;
import ru.otus.compositeservice.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;

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
