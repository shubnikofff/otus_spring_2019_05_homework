package ru.otus.web.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;
import ru.otus.repository.BookRepository;
import ru.otus.web.request.SaveBookRequest;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Service
@RequiredArgsConstructor
public class BookHandlerImpl implements BookHandler {

	private final BookRepository repository;

	@Override
	public Mono<ServerResponse> getAllBooks(ServerRequest request) {
		return ok().contentType(APPLICATION_JSON).body(repository.findAll(), Book.class);
	}

	@Override
	public Mono<ServerResponse> getBook(ServerRequest request) {
		return repository.findById(request.pathVariable("id"))
				.flatMap(book -> ok().contentType(APPLICATION_JSON).bodyValue(book))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	@Override
	public Mono<ServerResponse> createBook(ServerRequest request) {
		final Mono<Void> mono = request.bodyToMono(SaveBookRequest.class)
				.map(requestBody -> new Book(
						requestBody.getTitle(),
						new Genre(requestBody.getGenre()),
						requestBody.getAuthors().stream().map(Author::new).collect(toList())
				))
				.flatMap(repository::save)
				.then();

		return created(request.uri()).build(mono);
	}

	@Override
	public Mono<ServerResponse> updateBook(ServerRequest request) {
		return repository
				.findById(request.pathVariable("id"))
				.flatMap(book -> {
					final Mono<Void> mono = request.bodyToMono(SaveBookRequest.class)
							.map(requestBody -> {
								book.setTitle(requestBody.getTitle());
								book.setGenre(new Genre(requestBody.getGenre()));
								book.setAuthors(requestBody.getAuthors().stream().map(Author::new).collect(toList()));
								return book;
							})
							.flatMap(repository::save)
							.then();

					return noContent().build(mono);
				})
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	@Override
	public Mono<ServerResponse> deleteBook(ServerRequest request) {
		return repository
				.findById(request.pathVariable("id"))
				.flatMap(book -> noContent().build(repository.delete(book)))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
}
