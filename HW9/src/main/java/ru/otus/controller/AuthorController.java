package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;

@RequiredArgsConstructor
@Controller
public class AuthorController {

	private final AuthorRepository authorRepository;

	private final BookRepository bookRepository;

	@GetMapping("/author/list")
	ModelAndView findAllAuthors() {
		return new ModelAndView("author/list")
				.addObject("authors", authorRepository.findAll());
	}

	@GetMapping("/author/{name}/details")
	ModelAndView getAuthorDetails(@PathVariable("name") String name) {
		return new ModelAndView("author/details")
				.addObject("name", name)
				.addObject("books", bookRepository.findByAuthorName(name));
	}
}
