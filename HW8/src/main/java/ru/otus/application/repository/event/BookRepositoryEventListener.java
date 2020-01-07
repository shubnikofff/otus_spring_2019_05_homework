package ru.otus.application.repository.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;
import ru.otus.domain.repository.CommentRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookRepositoryEventListener extends AbstractMongoEventListener<Book> {

	private final CommentRepository commentRepository;

	@Override
	public void onAfterDelete(AfterDeleteEvent<Book> event) {
		super.onAfterDelete(event);
		final String bookId = event.getSource().get("_id").toString();
		final List<Comment> comments = commentRepository.findByBookId(bookId);
		commentRepository.deleteAll(comments);
	}
}
