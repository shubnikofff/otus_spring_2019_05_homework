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

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

	private final BookRepository bookRepository;

	private final CommentRepository commentRepository;

	@Override
	public List<Comment> getAll(String bookId) {
		return commentRepository.findByBookId(bookId);
	}

	@Override
	public void create(CreateCommentRequest request) {
		final Book book = bookRepository.findById(request.getBookId()).orElseThrow(BookNotFound::new);
		final Comment comment = new Comment(request.getUser(), request.getText(), book);
		commentRepository.save(comment);
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
