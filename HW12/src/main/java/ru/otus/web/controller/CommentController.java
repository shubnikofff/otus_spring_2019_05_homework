package ru.otus.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.web.exception.BookNotFound;
import ru.otus.web.request.CreateCommentRequest;
import ru.otus.web.service.CommentService;

@Controller
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@PostMapping(value = "/comment", params = "bookId")
	ModelAndView createComment(@RequestParam("bookId") String bookId, CreateCommentRequest request) {
		return commentService.getBook(bookId)
				.map(book -> {
					commentService.createComment(book, request);
					return new ModelAndView(String.format("redirect:/book/%s/details", book.getId()));
				})
				.orElseThrow(BookNotFound::new);
	}
}
