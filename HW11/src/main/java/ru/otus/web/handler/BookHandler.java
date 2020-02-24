package ru.otus.web.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface BookHandler {

	Mono<ServerResponse> getAll(ServerRequest request);

	Mono<ServerResponse> getOne(ServerRequest request);

	Mono<ServerResponse> create(ServerRequest request);

	Mono<ServerResponse> update(ServerRequest request);

	Mono<ServerResponse> delete(ServerRequest request);
}
