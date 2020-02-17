package ru.otus.repository.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.domain.model.Book;
import ru.otus.repository.CommentRepository;

@Component
@RequiredArgsConstructor
public class BookRepositoryEventListener extends AbstractMongoEventListener<Book> {

	private final CommentRepository commentRepository;

	@Override
	public void onAfterDelete(AfterDeleteEvent<Book> event) {
		super.onAfterDelete(event);
		final String bookId = event.getSource().get("id").toString();
		commentRepository.findByBookId(bookId).subscribe(comment -> commentRepository.delete(comment).subscribe());
	}
}
