package ru.otus.application.service.stringifier;

import org.springframework.stereotype.Service;
import ru.otus.domain.model.Comment;
import ru.otus.application.service.frontend.Options;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentStringifier implements Stringifier<Comment> {
	@Override
	public String stringify(Comment comment) {
		return comment.getText() +  "\n - " + comment.getUser();
	}

	public String stringify(List<Comment> comments) {
		return comments.stream().map(this::stringify).collect(Collectors.joining("\n\n"));
	}

	@Override
	public String stringify(Options<Comment> options) {
		return options.stream()
				.map(entry -> entry.getKey() + " - " + entry.getValue().getText())
				.collect(Collectors.joining("\n"));
	}
}
