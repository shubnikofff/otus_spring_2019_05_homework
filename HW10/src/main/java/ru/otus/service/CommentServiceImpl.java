package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;
import ru.otus.exception.BookNotFound;
import ru.otus.exception.CommentNotFound;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.request.CreateCommentRequest;
import ru.otus.request.UpdateCommentRequest;
import ru.otus.response.CommentResponse;
import ru.otus.response.CreateCommentResponse;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

	private final BookRepository bookRepository;

	private final CommentRepository commentRepository;

	@Override
	public List<CommentResponse> getAll(String bookId) {
		final List<Comment> comments = commentRepository.findByBookId(bookId);
		return comments.stream().map(comment -> new CommentResponse(
				comment.getId(),
				comment.getUser(),
				comment.getText(),
				comment.getBook().getId()
		)).collect(toList());
	}

	@Override
	public CreateCommentResponse create(CreateCommentRequest request) {
		final Book book = bookRepository.findById(request.getBookId()).orElseThrow(BookNotFound::new);
		final Comment comment = new Comment(request.getUser(), request.getText(), book);
		commentRepository.save(comment);
		return new CreateCommentResponse(comment.getId());
	}

	@Override
	public void update(String id, UpdateCommentRequest request) {
		final Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFound::new);
		comment.setUser(request.getUser());
		comment.setText(request.getText());
		commentRepository.save(comment);
	}

	@Override
	public void delete(String id) {
		final Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFound::new);
		commentRepository.delete(comment);
	}
}
