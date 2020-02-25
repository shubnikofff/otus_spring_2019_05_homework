package ru.otus.web.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.domain.model.Comment;
import ru.otus.repository.CommentRepository;
import ru.otus.web.request.CreateCommentRequest;
import ru.otus.web.request.UpdateCommentRequest;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

@RequiredArgsConstructor
@Service
public class CommentHandlerImpl implements CommentHandler {

	private final CommentRepository repository;

	@Override
	public Mono<ServerResponse> getAllComments(ServerRequest request) {
		return request.queryParam("bookId")
				.map(repository::findByBookId)
				.map(comments -> ok().contentType(MediaType.APPLICATION_JSON).body(comments, Comment.class))
				.orElse(notFound().build());
	}

	@Override
	public Mono<ServerResponse> createComment(ServerRequest request) {
		final Mono<Void> mono = request.bodyToMono(CreateCommentRequest.class)
				.map(requestBody -> new Comment(
						requestBody.getUser(),
						requestBody.getText(),
						requestBody.getBookId()
				))
				.flatMap(repository::save)
				.then();

		return created(request.uri()).build(mono);
	}

	@Override
	public Mono<ServerResponse> updateComment(ServerRequest request) {
		return repository
				.findById(request.pathVariable("id"))
				.flatMap(comment -> {
					final Mono<Void> mono = request.bodyToMono(UpdateCommentRequest.class)
							.map(requestBody -> {
								comment.setText(requestBody.getText());
								return comment;
							})
							.flatMap(repository::save)
							.then();

					return noContent().build(mono);
				})
				.switchIfEmpty(notFound().build());
	}

	@Override
	public Mono<ServerResponse> deleteComment(ServerRequest request) {
		return repository
				.findById(request.pathVariable("id"))
				.flatMap(comment -> noContent().build(repository.delete(comment)))
				.switchIfEmpty(notFound().build());
	}
}
