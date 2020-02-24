package ru.otus.web.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.domain.model.Author;
import ru.otus.repository.AuthorRepository;
import ru.otus.web.request.UpdateAuthorRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
@RequiredArgsConstructor
public class AuthorHandlerImpl implements AuthorHandler {

	private final AuthorRepository repository;

	@Override
	public Mono<ServerResponse> getAllAuthors(ServerRequest request) {
		return ok().contentType(APPLICATION_JSON).body(repository.findAll(), Author.class);
	}

	@Override
	public Mono<ServerResponse> updateAuthor(ServerRequest request) {
		return repository
				.findByName(request.pathVariable("name"))
				.flatMap(author -> {
					final Mono<Void> mono = request.bodyToMono(UpdateAuthorRequest.class)
							.map(UpdateAuthorRequest::getName)
							.flatMap(name -> repository.updateName(author, name))
							.then();

					return noContent().build(mono);
				})
				.switchIfEmpty(ServerResponse.notFound().build());
	}
}
