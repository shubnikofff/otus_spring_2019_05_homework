package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.model.Author;
import ru.otus.request.UpdateAuthorRequest;
import ru.otus.service.AuthorService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AuthorController {

	private final AuthorService service;

	@GetMapping("/authors")
	ResponseEntity<List<Author>> getAllAuthors() {
		final List<Author> authors = service.getAll();
		return new ResponseEntity<>(authors, HttpStatus.OK);
	}

	@PutMapping("/authors/{name}")
	ResponseEntity<HttpStatus> updateAuthor(@PathVariable("name") String name, @RequestBody UpdateAuthorRequest request) {
		service.update(name, request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
