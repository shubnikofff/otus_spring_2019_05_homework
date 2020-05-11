package ru.otus.reviewservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.reviewservice.dto.CommentDto;
import ru.otus.reviewservice.service.CommentService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@GetMapping("/")
	public ResponseEntity<Collection<CommentDto>> getAllByBookId(@RequestParam String bookId) {
		return new ResponseEntity<>(commentService.getAll(bookId), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<String> createComment(@RequestParam String bookId, @RequestBody CommentDto commentDto) {
		return new ResponseEntity<>(commentService.create(bookId, commentDto), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<HttpStatus> updateComment(@PathVariable("id") String id, @RequestBody CommentDto commentDto) {
		commentService.update(id, commentDto);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") String id) {
		commentService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
