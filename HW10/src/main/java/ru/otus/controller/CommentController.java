package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.model.Comment;
import ru.otus.request.CreateCommentRequest;
import ru.otus.request.UpdateCommentRequest;
import ru.otus.service.CommentService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

	private final CommentService service;

	@GetMapping("/comments")
	ResponseEntity<List<Comment>> getAll(@RequestParam("bookId") String bookId) {
		final List<Comment> comments = service.getAll(bookId);
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}

	@PostMapping("/comments")
	ResponseEntity<HttpStatus> create(@RequestBody CreateCommentRequest request) {
		service.create(request);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/comments/{id}")
	ResponseEntity<HttpStatus> update(@PathVariable("id") String id, @RequestBody UpdateCommentRequest request) {
		service.update(id, request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/comments/{id}")
	ResponseEntity<HttpStatus> delete(@PathVariable("id") String id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
