package ru.otus.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.repository.UserRepository;
import ru.otus.web.request.CreateCommentRequest;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final BookRepository bookRepository;

	private final CommentRepository commentRepository;

	private final UserRepository userRepository;

	@Override
	public Optional<Book> getBook(String id) {
		return bookRepository.findById(id);
	}

	@Override
	@Secured("ROLE_USER")
	public Comment createComment(Book book, CreateCommentRequest request) {
		final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return userRepository.findByUsername(userDetails.getUsername())
				.map(user -> commentRepository.insert(new Comment(
						user.getFullName(),
						request.getText(),
						book
				)))
				.orElseThrow(RuntimeException::new);
	}
}
