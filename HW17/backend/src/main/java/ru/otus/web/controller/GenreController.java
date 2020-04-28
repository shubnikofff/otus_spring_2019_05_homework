package ru.otus.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.Genre;
import ru.otus.web.request.UpdateGenreRequest;
import ru.otus.web.service.GenreService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class GenreController {

	private final GenreService service;

	@GetMapping("/genre")
	ResponseEntity<List<Genre>> getAllGenres() {
		final List<Genre> genres = service.getAll();
		return new ResponseEntity<>(genres, HttpStatus.OK);
	}

	@PutMapping("/genre/{name}")
	ResponseEntity<HttpStatus> updateGenre(@PathVariable("name") String name, @RequestBody UpdateGenreRequest request) {
		service.update(name, request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
