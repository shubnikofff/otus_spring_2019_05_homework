package ru.otus.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.model.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String>, BookRepositoryCustom {

	Mono<Book> findByTitle(String title);

	Flux<Book> findByGenreName(String name);
}
