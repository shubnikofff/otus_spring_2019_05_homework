package ru.otus.web.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface BookHandler {

	Mono<ServerResponse> getAllBooks(ServerRequest request);

	Mono<ServerResponse> getBook(ServerRequest request);

	Mono<ServerResponse> createBook(ServerRequest request);

	Mono<ServerResponse> updateBook(ServerRequest request);

	Mono<ServerResponse> deleteBook(ServerRequest request);
}
