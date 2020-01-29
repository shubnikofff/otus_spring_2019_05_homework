package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.controller.form.AuthorForm;
import ru.otus.domain.exception.NotFoundException;
import ru.otus.domain.model.Author;
import ru.otus.repository.AuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AuthorController {

	private final AuthorRepository repository;

	@GetMapping("/author")
	List<Author> getAllAuthors() {
		return repository.findAll();
	}

	@PutMapping("/author/{name}")
	ResponseEntity<HttpStatus> update(@PathVariable("name") String name, @RequestBody AuthorForm form) {
		final Author author = repository.findByName(name).orElseThrow(() -> new NotFoundException("Author not found"));
		repository.updateName(author, form.getName());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
