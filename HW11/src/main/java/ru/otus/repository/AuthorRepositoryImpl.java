package ru.otus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;

import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {

	private final ReactiveMongoOperations mongoOperations;

	@Override
	public Flux<Author> findAll() {
		final Aggregation aggregation = newAggregation(
				unwind("authors"),
				group("authors.name"),
				project().and("_id").as("name").andExclude("_id"),
				sort(by("name").ascending())
		);

		return mongoOperations.aggregate(aggregation, Book.class, Author.class);
	}

	@Override
	public Mono<Author> findByName(String name) {
		final Aggregation aggregation = newAggregation(
				unwind("authors"),
				group("authors.name"),
				match(where("_id").is(name)),
				project().and("_id").as("name").andExclude("_id")
		);

		return mongoOperations.aggregate(aggregation, Book.class, Author.class).single();
	}

	@Override
	public Mono<Void> updateName(Author author, String newName) {
		final Update update = new Update()
				.filterArray(where("element.name").is(author.getName()))
				.set( "authors.$[element].name", newName);

		return mongoOperations.updateMulti(new Query(), update, Book.class).then();
	}
}
