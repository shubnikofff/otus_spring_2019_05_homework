package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.domain.exception.NotFoundException;
import ru.otus.domain.model.Book;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;

@RequiredArgsConstructor
@Controller
public class BookController {

	private final BookRepository bookRepository;

	private final CommentRepository commentRepository;

	@GetMapping("/")
	String list(Model model) {
		model.addAttribute("books", bookRepository.findAll());
		return "book/list";
	}

	@GetMapping("/book/{id}/details")
	String details(@PathVariable("id") String id, Model model) {
		final Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book was not found"));
		model.addAttribute("book", book);
		model.addAttribute("comments", commentRepository.findByBookId(book.getId()));
		return "book/details";
	}
}
