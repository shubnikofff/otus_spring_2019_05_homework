package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.controller.form.GenreForm;
import ru.otus.domain.exception.NotFoundException;
import ru.otus.domain.model.Genre;
import ru.otus.repository.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class GenreController {

	private final GenreRepository repository;

	@GetMapping("/genre")
	List<Genre> getAllGenres() {
		return repository.findAll();
	}

	@PutMapping("/genre/{name}")
	ResponseEntity<HttpStatus> update(@PathVariable("name") String name, @RequestBody GenreForm form) {
		final Genre genre = repository.findByName(name).orElseThrow(() -> new NotFoundException("Genre not found"));
		repository.updateName(genre, form.getName());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
