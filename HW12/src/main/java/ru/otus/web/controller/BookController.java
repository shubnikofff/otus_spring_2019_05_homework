package ru.otus.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.web.exception.BookNotFound;
import ru.otus.web.request.SaveBookRequest;
import ru.otus.web.service.BookService;

import static java.util.stream.Collectors.joining;

@RequiredArgsConstructor
@Controller
public class BookController {

	private final BookService bookService;

	@GetMapping("/book/list")
	ModelAndView getAllBooks() {
		return new ModelAndView("book/list")
				.addObject("books", bookService.getAllBooks());
	}

	@GetMapping("/book/{id}/details")
	ModelAndView getBook(@PathVariable("id") String id) {
		return bookService.getBook(id)
				.map(book -> new ModelAndView("book/details")
						.addObject("book", book)
						.addObject("comments", bookService.getBookComments(book)))
				.orElseThrow(BookNotFound::new);
	}

	@GetMapping("/book/create")
	ModelAndView getCreateBookView() {
		return new ModelAndView("book/form");
	}

	@PostMapping("/book/create")
	ModelAndView createBook(SaveBookRequest request) {
		final Book book = bookService.createBook(request);
		return new ModelAndView(String.format("redirect:/book/%s/details", book.getId()))
				.addObject("book", book);
	}

	@GetMapping("/book/{id}/update")
	ModelAndView getUpdateBookView(@PathVariable("id") String id) {
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
	ModelAndView updateBook(@PathVariable("id") String id, SaveBookRequest request) {
		return bookService.getBook(id)
				.map(book -> {
					final Book result = bookService.updateBook(book, request);
					return new ModelAndView(String.format("redirect:/book/%s/details", result.getId()));
				})
				.orElseThrow(BookNotFound::new);
	}

	@GetMapping("/book/{id}/delete")
	ModelAndView getDeleteBookView(@PathVariable("id") String id) {
		return bookService.getBook(id)
				.map(book -> new ModelAndView("book/delete")
						.addObject("book", book))
				.orElseThrow(BookNotFound::new);
	}

	@PostMapping("/book/{id}/delete")
	ModelAndView deleteBook(@PathVariable("id") String id) {
		return bookService.getBook(id)
				.map(book -> {
					bookService.deleteBook(book);
					return new ModelAndView("redirect:/");
				})
				.orElseThrow(BookNotFound::new);
	}
}
