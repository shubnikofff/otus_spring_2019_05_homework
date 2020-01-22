package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.exception.NotFoundException;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Controller
public class BookController {

	private final BookRepository bookRepository;

	private final CommentRepository commentRepository;

	@GetMapping("/")
	String getBookList(Model model) {
		model.addAttribute("books", bookRepository.findAll());
		return "book/list";
	}

	@GetMapping("/book/{id}/details")
	String getBookDetails(@PathVariable("id") String id, Model model) {
		final Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book was not found"));
		model.addAttribute("book", book);
		model.addAttribute("comments", commentRepository.findByBookId(book.getId()));
		return "book/details";
	}

	@GetMapping("/book/{id}/update")
	String getBookForm(@PathVariable("id") String id, Model model) {
		final Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book was not found"));
		model.addAttribute("book", book);
		final String authorListAsString = book.getAuthors().stream().map(Author::getName).collect(joining(", "));
		model.addAttribute("authorsListAsString", authorListAsString);
		return "book/form";
	}

	@PostMapping("/book/{id}/update")
	String updateBook(@PathVariable("id") String id, @ModelAttribute BookForm form, Model model) {
		final Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book was not found"));
		book.setTitle(form.getTitle());
		book.setGenre(new Genre(form.getGenre()));
		book.setAuthors(getAuthorListFromString(form.getAuthors()));
		bookRepository.save(book);
		model.addAttribute("book", book);
		model.addAttribute("comments", commentRepository.findByBookId(book.getId()));
		return "book/details";
	}

	private static List<Author> getAuthorListFromString(String str) {
		return Arrays.stream(str.split("\\s*,\\s*")).map(Author::new).collect(toList());
	}
}
