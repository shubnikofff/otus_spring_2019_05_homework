package ru.otus.bookregistry.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import ru.otus.bookregistry.model.Author;
import ru.otus.bookregistry.model.Book;

import java.util.Collection;
import java.util.Optional;

import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {

	private final MongoOperations mongoOperations;

	@Override
	public Collection<Author> findAll() {
		final Aggregation aggregation = newAggregation(
				unwind("authors"),
				group("authors.name"),
				project().and("_id").as("name").andExclude("_id"),
				sort(by("name").ascending())
		);

		return mongoOperations.aggregate(aggregation, Book.class, Author.class).getMappedResults();
	}

	@Override
	public Optional<Author> findById(String id) {
		final Aggregation aggregation = newAggregation(
				unwind("authors"),
				group("authors.name"),
				match(where("_id").is(id)),
				project().and("_id").as("name").andExclude("_id")
		);

		return Optional.ofNullable(mongoOperations.aggregate(aggregation, Book.class, Author.class).getUniqueMappedResult());
	}

	@Override
	public void updateName(Author author, String newName) {
		final Update update = new Update()
				.filterArray(where("element.name").is(author.getName()))
				.set("authors.$[element].name", newName);

		mongoOperations.updateMulti(new Query(), update, Book.class);
	}
}
