package ru.otus.reviewservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.reviewservice.dto.CommentDto;
import ru.otus.reviewservice.exception.CommentNotFoundException;
import ru.otus.reviewservice.model.Comment;
import ru.otus.reviewservice.repository.CommentRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;

	@HystrixCommand(commandKey = "getAllComments", fallbackMethod = "getAllFallback")
	@Override
	public Collection<CommentDto> getAll(String bookId) {
		return commentRepository.findByBookId(bookId).stream()
				.map(comment -> new CommentDto(
						comment.getId(),
						comment.getUsername(),
						comment.getText()
				))
				.collect(Collectors.toList());
	}

	private Collection<CommentDto> getAllFallback(String bookId) {
		return Collections.emptyList();
	}

	@HystrixCommand(commandKey = "createComment", fallbackMethod = "createFallback")
	@Override
	public String create(String bookId, CommentDto commentDto) {
		final Comment comment = commentRepository.save(new Comment(
				null,
				commentDto.getUsername(),
				commentDto.getText(),
				bookId
		));
		return comment.getId();
	}

	private String createFallback(String bookId, CommentDto commentDto) {
		return null;
	}

	@HystrixCommand(commandKey = "updateComment", fallbackMethod = "updateFallback")
	@Override
	public void update(String id, CommentDto commentDto) {
		final Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
		comment.setText(commentDto.getText());
		commentRepository.save(comment);
	}

	void updateFallback(String id, CommentDto commentDto) {
	}

	@HystrixCommand(commandKey = "deleteComment", fallbackMethod = "deleteFallback")
	@Override
	public void delete(String id) {
		final Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
		commentRepository.delete(comment);
	}

	private void deleteFallback(String id) {
	}
}
