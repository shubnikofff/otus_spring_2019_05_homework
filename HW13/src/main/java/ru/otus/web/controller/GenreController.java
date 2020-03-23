package ru.otus.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.domain.Genre;
import ru.otus.web.exception.GenreNotFound;
import ru.otus.web.request.UpdateGenreRequest;
import ru.otus.web.service.GenreService;

@Controller
@RequiredArgsConstructor
public class GenreController {

	private final GenreService genreService;

	@GetMapping("/genre/list")
	public ModelAndView getAllGenres() {
		return new ModelAndView("genre/list")
				.addObject("genres", genreService.getAllGenres());
	}

	@GetMapping("/genre/{name}/details")
	public ModelAndView getGenre(@PathVariable("name") String name) {
		return genreService.getGenre(name)
				.map(genre -> new ModelAndView("genre/details")
						.addObject("name", genre.getName())
						.addObject("books", genreService.getBooksOfGenre(genre)))
				.orElseThrow(GenreNotFound::new);
	}

	@PostMapping("/genre/{name}/update")
	public ModelAndView updateGenre(@PathVariable("name") String name, UpdateGenreRequest request) {
		return genreService.getGenre(name)
				.map(genre -> {
					final Genre result = genreService.updateGenre(genre, request);
					return new ModelAndView(String.format("redirect:/genre/%s/details", result.getName()));
				})
				.orElseThrow(GenreNotFound::new);
	}
}
