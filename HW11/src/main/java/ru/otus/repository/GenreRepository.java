package ru.otus.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.model.Genre;

public interface GenreRepository {

	Flux<Genre> findAll();

	Mono<Genre> findByName(String name);

	Mono<Void> updateName(Genre genre, String newName);
}
