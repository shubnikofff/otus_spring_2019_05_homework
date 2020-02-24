package ru.otus.web.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface AuthorHandler {

	Mono<ServerResponse> getAllAuthors(ServerRequest request);

	Mono<ServerResponse> updateAuthor(ServerRequest request);
}
