package ru.otus.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.domain.Comment;
import ru.otus.web.exception.BookNotFound;
import ru.otus.web.request.SaveCommentRequest;
import ru.otus.web.service.CommentService;

@Controller
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@PostMapping(value = "/comment/create", params = "bookId")
	public ModelAndView createComment(@RequestParam("bookId") String bookId, SaveCommentRequest request) {
		return commentService.getBook(bookId)
				.map(book -> {
					commentService.createComment(book, request);
					return new ModelAndView(String.format("redirect:/book/%s/details", book.getId()));
				})
				.orElseThrow(BookNotFound::new);
	}

	@GetMapping("/comment/{id}/update")
	public ModelAndView getUpdateCommentView(@PathVariable("id") Long id) {
		return new ModelAndView("comment/form")
				.addObject("comment", commentService.getComment(id));
	}

	@PostMapping("/comment/{id}/update")
	public ModelAndView updateComment(@PathVariable("id") Long id, SaveCommentRequest request) {
		final Comment comment = commentService.updateComment(id, request);
		return new ModelAndView(String.format("redirect:/book/%s/details", comment.getBook().getId()));
	}
}
