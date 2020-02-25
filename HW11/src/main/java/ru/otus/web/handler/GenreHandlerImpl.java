package ru.otus.web.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.domain.model.Genre;
import ru.otus.repository.GenreRepository;
import ru.otus.web.request.UpdateGenreRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@RequiredArgsConstructor
@Service
public class GenreHandlerImpl implements GenreHandler {

	private final GenreRepository repository;

	@Override
	public Mono<ServerResponse> getAllGenres(ServerRequest request) {
		return ok().contentType(APPLICATION_JSON).body(repository.findAll(), Genre.class);
	}

	@Override
	public Mono<ServerResponse> updateGenre(ServerRequest request) {
		return repository
				.findByName(request.pathVariable("name"))
				.flatMap(genre -> {
					final Mono<Void> mono = request.bodyToMono(UpdateGenreRequest.class)
							.map(UpdateGenreRequest::getName)
							.flatMap(name -> repository.updateName(genre, name))
							.then();

					return noContent().build(mono);
				})
				.switchIfEmpty(notFound().build());
	}
}
