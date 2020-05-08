package ru.otus.bookregistry.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.bookregistry.dto.GenreDto;
import ru.otus.bookregistry.service.GenreService;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
public class GenreController {

	private final GenreService genreService;

	@GetMapping("/genre")
	ResponseEntity<Collection<GenreDto>> getAllAuthors() {
		final Collection<GenreDto> genres = genreService.getAll();
		return new ResponseEntity<>(genres, HttpStatus.OK);
	}

	@PutMapping(value = "/genre/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<HttpStatus> updateAuthor(@PathVariable("id") String id, @RequestBody GenreDto genreDto) {
		genreService.update(id, genreDto);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
