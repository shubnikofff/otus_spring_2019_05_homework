package ru.otus.bookregistry.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.bookregistry.dto.AuthorDto;
import ru.otus.bookregistry.service.AuthorService;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
public class AuthorController {

	private final AuthorService authorService;

	@GetMapping("/author")
	ResponseEntity<?> getAllAuthors() {
		return ResponseEntity.ok(authorService.getAll());
	}

	@PutMapping(value = "/author/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> updateAuthor(@PathVariable("id") String id, @RequestBody AuthorDto authorDto) {
		authorService.update(id, authorDto);
		return ResponseEntity.noContent().build();
	}
}
