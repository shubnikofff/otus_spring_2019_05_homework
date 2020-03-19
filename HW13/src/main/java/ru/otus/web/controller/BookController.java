package ru.otus.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.web.exception.BookNotFound;
import ru.otus.web.request.SaveBookRequest;
import ru.otus.web.service.BookService;

import static java.util.stream.Collectors.joining;

@RequiredArgsConstructor
@Controller
public class BookController {

	private final BookService bookService;

	@GetMapping("/book/list")
	public ModelAndView getAllBooks() {
		return new ModelAndView("book/list")
				.addObject("books", bookService.getAllBooks());
	}

	@GetMapping("/book/{id}/details")
	public ModelAndView getBook(@PathVariable("id") String id) {
		return bookService.getBook(id)
				.map(book -> new ModelAndView("book/details")
						.addObject("book", book)
						.addObject("comments", bookService.getBookComments(book)))
				.orElseThrow(BookNotFound::new);
	}

	@GetMapping("/book/create")
	@Secured("ADMIN")
	public ModelAndView getCreateBookView() {
		return new ModelAndView("book/form");
	}

	@PostMapping("/book/create")
	@Secured("ADMIN")
	public ModelAndView createBook(SaveBookRequest request) {
		final Book book = bookService.createBook(request);
		return new ModelAndView(String.format("redirect:/book/%s/details", book.getId()))
				.addObject("book", book);
	}

	@GetMapping("/book/{id}/update")
	@Secured("ADMIN")
	public ModelAndView getUpdateBookView(@PathVariable("id") String id) {
		return bookService.getBook(id)
				.map(book -> new ModelAndView("book/form")
						.addObject("id", book.getId())
						.addObject("bookTitle", book.getTitle())
						.addObject("genre", book.getGenre().getName())
						.addObject("authors", book.getAuthors().stream().map(Author::getName).collect(joining(", ")))
				)
				.orElseThrow(BookNotFound::new);
	}

	@PostMapping("/book/{id}/update")
	@Secured("ADMIN")
	public ModelAndView updateBook(@PathVariable("id") String id, SaveBookRequest request) {
		return bookService.getBook(id)
				.map(book -> {
					final Book result = bookService.updateBook(book, request);
					return new ModelAndView(String.format("redirect:/book/%s/details", result.getId()));
				})
				.orElseThrow(BookNotFound::new);
	}

	@GetMapping("/book/{id}/delete")
	@Secured("ADMIN")
	public ModelAndView getDeleteBookView(@PathVariable("id") String id) {
		return bookService.getBook(id)
				.map(book -> new ModelAndView("book/delete")
						.addObject("book", book))
				.orElseThrow(BookNotFound::new);
	}

	@PostMapping("/book/{id}/delete")
	@Secured("ADMIN")
	public ModelAndView deleteBook(@PathVariable("id") String id) {
		return bookService.getBook(id)
				.map(book -> {
					bookService.deleteBook(book);
					return new ModelAndView("redirect:/");
				})
				.orElseThrow(BookNotFound::new);
	}
}
