package ru.otus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryImpl implements GenreRepository {

	private final ReactiveMongoOperations mongoOperations;

	@Override
	public Flux<Genre> findAll() {
		return mongoOperations.findDistinct("genre", Book.class, Genre.class);
	}

	@Override
	public Mono<Genre> findByName(String name) {
		final Aggregation aggregation = newAggregation(
				match(where("genre.name").is(name)),
				group("genre.name"),
				project().and("_id").as("name").andExclude("_id")
		);

		return mongoOperations.aggregate(aggregation, Book.class, Genre.class).singleOrEmpty();
	}

	@Override
	public Mono<Void> updateName(Genre genre, String newName) {
		return mongoOperations.updateMulti(
				query(where("genre.name").is(genre.getName())),
				update("genre", new Genre(newName)),
				Book.class
		).then();
	}
}
