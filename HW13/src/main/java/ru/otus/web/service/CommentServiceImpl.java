package ru.otus.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.acls.domain.BasePermission;
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
import ru.otus.web.request.CreateCommentRequest;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final MutableAclService  aclService;

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
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		return userRepository.findByUsername(userDetails.getUsername())
				.map(user -> commentRepository.insert(new Comment(
						user.getFullName(),
						request.getText(),
						book
				)))
				.map(comment -> {
					final PrincipalSid owner = new PrincipalSid(authentication);
					final MutableAcl acl = aclService.createAcl(new ObjectIdentityImpl(Comment.class, comment.getId()));
					acl.setOwner(owner);
					acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, owner, true);
					aclService.updateAcl(acl);
					return comment;
				})
				.orElseThrow(RuntimeException::new);
	}
}
