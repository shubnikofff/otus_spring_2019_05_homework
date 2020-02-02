package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.model.Genre;
import ru.otus.request.UpdateGenreRequest;
import ru.otus.service.GenreService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class GenreController {

	private final GenreService service;

	@GetMapping("/genres")
	ResponseEntity<List<Genre>> getAllGenres() {
		final List<Genre> genres = service.getAll();
		return new ResponseEntity<>(genres, HttpStatus.OK);
	}

	@PutMapping("/genres/{name}")
	ResponseEntity<HttpStatus> update(@PathVariable("name") String name, @RequestBody UpdateGenreRequest request) {
		service.update(name, request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
