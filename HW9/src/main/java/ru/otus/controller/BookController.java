package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.controller.form.BookForm;
import ru.otus.controller.form.CommentForm;
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
	ModelAndView getBookList() {
		return new ModelAndView("book/list", HttpStatus.OK)
				.addObject("books", bookRepository.findAll());
	}

	@GetMapping("/book/{id}/details")
	ModelAndView getBookDetails(@PathVariable("id") String id) {
		final Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book was not found"));
		return new ModelAndView("book/details", HttpStatus.OK)
				.addObject("book", book)
				.addObject("comments", commentRepository.findByBookId(book.getId()));
	}

	@GetMapping("/book/create")
	String getNewBookForm() {
		return "book/form";
	}

	@PostMapping("/book/create")
	ModelAndView createBook(@ModelAttribute BookForm form) {
		final Book book = bookRepository.save(Mapper.map(form));
		return new ModelAndView("redirect:/book/" + book.getId() + "/details")
				.addObject("book", book);
	}

	@GetMapping("/book/{id}/update")
	ModelAndView getBookForm(@PathVariable("id") String id) {
		final Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book was not found"));
		return new ModelAndView("book/form", HttpStatus.OK)
				.addObject("id", id)
				.addObject("book", Mapper.map(book));
	}

	@PostMapping("/book/{id}/update")
	ModelAndView updateBook(@PathVariable("id") String id, @ModelAttribute BookForm form) {
		final Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book was not found"));
		bookRepository.save(Mapper.map(form, book));
		return new ModelAndView("redirect:/book/" + book.getId() + "/details");
	}

	@PostMapping("/book/{id}/add-comment")
	ModelAndView addComment(@PathVariable("id") String bookId, @ModelAttribute CommentForm form) {
		final Book book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Book was not found"));
		commentRepository.save(Mapper.map(form, book));
		return new ModelAndView("redirect:/book/" + book.getId() + "/details");
	}

	@GetMapping("/book/{id}/delete")
	ModelAndView getDeleteForm(@PathVariable("id") String id) {
		final Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book was not found"));
		return new ModelAndView("book/delete")
				.addObject("book", book);
	}

	@PostMapping("/book/{id}/delete")
	ModelAndView deleteBook(@PathVariable("id") String id) {
		bookRepository.deleteById(id);
		return new ModelAndView("redirect:/");

	}
}
