package ru.otus.reviewservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.reviewservice.dto.CommentDto;
import ru.otus.reviewservice.exception.CommentNotFoundException;
import ru.otus.reviewservice.model.Comment;
import ru.otus.reviewservice.repository.CommentRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;

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

	@Override
	public void update(String id, CommentDto commentDto) {
		final Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
		comment.setText(commentDto.getText());
		commentRepository.save(comment);
	}

	@Override
	public void delete(String id) {
		final Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
		commentRepository.delete(comment);
	}
}
