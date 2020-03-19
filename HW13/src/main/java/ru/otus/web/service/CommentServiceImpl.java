package ru.otus.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.repository.UserRepository;
import ru.otus.web.exception.CommentNotFound;
import ru.otus.web.request.SaveCommentRequest;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final MutableAclService aclService;

	private final BookRepository bookRepository;

	private final CommentRepository commentRepository;

	private final UserRepository userRepository;

	@Override
	public Optional<Book> getBook(String id) {
		return bookRepository.findById(id);
	}

	@Override
	public Comment getComment(Long id) {
		return Optional.ofNullable(commentRepository.findCommentById(id)).orElseThrow(CommentNotFound::new);
	}

	@Override
	public Comment createComment(Book book, SaveCommentRequest request) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		return userRepository.findByUsername(userDetails.getUsername())
				.map(user -> commentRepository.insert(new Comment(
						user.getFullName(),
						request.getText(),
						book
				)))
				.map(comment -> {
					final MutableAcl acl = aclService.createAcl(new ObjectIdentityImpl(Comment.class, comment.getId()));
					acl.insertAce(acl.getEntries().size(), BasePermission.READ, new GrantedAuthoritySid("ROLE_USER"), true);
					acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, new PrincipalSid(authentication), true);
					aclService.updateAcl(acl);
					return comment;
				})
				.orElseThrow(RuntimeException::new);
	}

	@Override
	public Comment updateComment(Long id, SaveCommentRequest request) {
		return Optional.ofNullable(commentRepository.findCommentById(id))
				.map(comment -> {
					comment.setText(request.getText());
					return commentRepository.save(comment);
				})
				.orElseThrow(CommentNotFound::new);
	}
}
