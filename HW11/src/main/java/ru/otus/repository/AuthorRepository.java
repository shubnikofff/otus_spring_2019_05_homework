package ru.otus.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.model.Author;

public interface AuthorRepository {

	Flux<Author> findAll();

	Mono<Void> updateName(Author author, String newName);

	Mono<Author> findByName(String name);
}
