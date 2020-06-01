package ru.otus.bookregistry.controller;

import lombok.RequiredArgsConstructor;
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
		return ResponseEntity.ok(genreService.getAll());
	}

	@PutMapping(value = "/genre/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> updateAuthor(@PathVariable("id") String id, @RequestBody GenreDto genreDto) {
		genreService.update(id, genreDto);
		return ResponseEntity.noContent().build();
	}
}
