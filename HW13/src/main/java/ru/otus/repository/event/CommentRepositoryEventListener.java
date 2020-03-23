package ru.otus.repository.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.domain.Comment;
import ru.otus.repository.id.generator.IdGenerator;

@RequiredArgsConstructor
@Component
public class CommentRepositoryEventListener extends AbstractMongoEventListener<Comment> {

	private final IdGenerator idGenerator;

	@Override
	public void onBeforeConvert(BeforeConvertEvent<Comment> event) {
		super.onBeforeConvert(event);
		if(event.getSource().getId() == null) {
			event.getSource().setId(idGenerator.getId(Comment.SEQUENCE_NAME));
		}
	}
}
