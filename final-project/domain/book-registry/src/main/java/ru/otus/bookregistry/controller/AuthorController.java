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
	ResponseEntity<Collection<AuthorDto>> getAllAuthors() {
		final Collection<AuthorDto> authors = authorService.getAll();
		return new ResponseEntity<>(authors, HttpStatus.OK);
	}

	@PutMapping(value = "/author/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<HttpStatus> updateAuthor(@PathVariable("id") String id, @RequestBody AuthorDto authorDto) {
		authorService.update(id, authorDto);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
