package ru.otus.web.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface CommentHandler {

	Mono<ServerResponse> getAllComments(ServerRequest request);

	Mono<ServerResponse> createComment(ServerRequest request);

	Mono<ServerResponse> updateComment(ServerRequest request);

	Mono<ServerResponse> deleteComment(ServerRequest request);
}
