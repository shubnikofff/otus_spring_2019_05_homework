package ru.otus.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.web.request.CreateCommentRequest;
import ru.otus.web.request.UpdateCommentRequest;
import ru.otus.web.response.CommentResponse;
import ru.otus.web.response.CreateCommentResponse;
import ru.otus.web.service.CommentService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

	private final CommentService service;

	@GetMapping("/comment")
	ResponseEntity<List<CommentResponse>> getAllComments(@RequestParam("bookId") String bookId) {
		final List<CommentResponse> comments = service.getAll(bookId);
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}

	@PostMapping("/comment")
	ResponseEntity<CreateCommentResponse> createComment(@RequestBody CreateCommentRequest request) {
		final CreateCommentResponse response = service.create(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/comment/{id}")
	ResponseEntity<HttpStatus> updateComment(@PathVariable("id") String id, @RequestBody UpdateCommentRequest request) {
		service.update(id, request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/comment/{id}")
	ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") String id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
