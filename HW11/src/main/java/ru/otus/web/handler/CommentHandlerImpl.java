package ru.otus.web.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.domain.model.Comment;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.web.request.CreateCommentRequest;
import ru.otus.web.request.UpdateCommentRequest;
import ru.otus.web.response.CreateCommentResponse;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

@RequiredArgsConstructor
@Service
public class CommentHandlerImpl implements CommentHandler {

	private final BookRepository bookRepository;

	private final CommentRepository commentRepository;

	@Override
	public Mono<ServerResponse> getAllComments(ServerRequest request) {
		return request.queryParam("bookId")
				.map(commentRepository::findByBookId)
				.map(comments -> ok().contentType(MediaType.APPLICATION_JSON).body(comments, Comment.class))
				.orElse(notFound().build());
	}

	@Override
	public Mono<ServerResponse> createComment(ServerRequest request) {
		return request.bodyToMono(CreateCommentRequest.class)
				.flatMap(requestBody -> bookRepository.findById(requestBody.getBookId())
						.map(book -> new Comment(
								requestBody.getUser(),
								requestBody.getText(),
								book.getId()
						))
						.flatMap(commentRepository::save)
						.map(comment -> new CreateCommentResponse(comment.getId()))
						.flatMap(response -> created(request.uri()).body(Mono.just(response), CreateCommentResponse.class))
						.switchIfEmpty(notFound().build()));
	}

	@Override
	public Mono<ServerResponse> updateComment(ServerRequest request) {
		return commentRepository
				.findById(request.pathVariable("id"))
				.flatMap(comment -> {
					final Mono<Void> mono = request.bodyToMono(UpdateCommentRequest.class)
							.map(requestBody -> {
								comment.setText(requestBody.getText());
								return comment;
							})
							.flatMap(commentRepository::save)
							.then();

					return noContent().build(mono);
				})
				.switchIfEmpty(notFound().build());
	}

	@Override
	public Mono<ServerResponse> deleteComment(ServerRequest request) {
		return commentRepository
				.findById(request.pathVariable("id"))
				.flatMap(comment -> noContent().build(commentRepository.delete(comment)))
				.switchIfEmpty(notFound().build());
	}
}
