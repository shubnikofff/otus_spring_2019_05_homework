package ru.otus.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.domain.Author;
import ru.otus.web.exception.AuthorNotFound;
import ru.otus.web.request.UpdateAuthorRequest;
import ru.otus.web.service.AuthorService;

@RequiredArgsConstructor
@Controller
public class AuthorController {

	private final AuthorService authorService;

	@GetMapping("/author/list")
	public ModelAndView getAllAuthors() {
		return new ModelAndView("author/list")
				.addObject("authors", authorService.getAllAuthors());
	}

	@GetMapping("/author/{name}/details")
	public ModelAndView getAuthor(@PathVariable("name") String name) {
		return authorService.getAuthor(name)
				.map(author -> new ModelAndView("author/details")
						.addObject("name", author.getName())
						.addObject("books", authorService.getAuthorBooks(author)))
				.orElseThrow(AuthorNotFound::new);
	}

	@PostMapping("/author/{name}")
	public ModelAndView updateAuthor(@PathVariable("name") String name, UpdateAuthorRequest request) {
		return authorService.getAuthor(name)
				.map(author -> {
					final Author result = authorService.updateAuthor(author, request);
					return new ModelAndView(String.format("redirect:/author/%s/details", result.getName()));
				})
				.orElseThrow(AuthorNotFound::new);
	}
}
