package ru.otus.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.Author;
import ru.otus.web.request.UpdateAuthorRequest;
import ru.otus.web.service.AuthorService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AuthorController {

	private final AuthorService service;

	@GetMapping("/author")
	ResponseEntity<List<Author>> getAllAuthors() {
		final List<Author> authors = service.getAll();
		return new ResponseEntity<>(authors, HttpStatus.OK);
	}

	@PutMapping("/author/{name}")
	ResponseEntity<HttpStatus> updateAuthor(@PathVariable("name") String name, @RequestBody UpdateAuthorRequest request) {
		service.update(name, request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
