package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

@RequiredArgsConstructor
@Controller
public class GenreController {

	private final BookRepository bookRepository;

	private final GenreRepository genreRepository;

	@GetMapping("/genre/list")
	ModelAndView getGenreList() {
		return new ModelAndView("genre/list")
				.addObject("genres", genreRepository.findAll());
	}

	@GetMapping("genre/{name}/details")
	ModelAndView getGenreDetails(@PathVariable("name") String name) {
		return new ModelAndView("genre/details")
				.addObject("name", name)
				.addObject("books", bookRepository.findByGenreName(name));
	}
}
