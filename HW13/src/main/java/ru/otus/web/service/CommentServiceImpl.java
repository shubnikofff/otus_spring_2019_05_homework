package ru.otus.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.web.request.CreateCommentRequest;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final BookRepository bookRepository;

	private final CommentRepository commentRepository;

	@Override
	public Optional<Book> getBook(String id) {
		return bookRepository.findById(id);
	}

	@Override
	public Comment createComment(Book book, CreateCommentRequest request) {
		return commentRepository.insert(new Comment(
				request.getUser(),
				request.getText(),
				book
		));
	}
}
