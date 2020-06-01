package ru.otus.bookregistry.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Repository;
import ru.otus.bookregistry.model.Book;
import ru.otus.bookregistry.model.Genre;

import java.util.Collection;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryImpl implements GenreRepository {

	private final MongoOperations mongoOperations;

	@Override
	public Collection<Genre> findAll() {
		return mongoOperations.findDistinct("genre", Book.class, Genre.class);
	}

	@Override
	public Optional<Genre> findByName(String name) {
		final Aggregation aggregation = newAggregation(
				match(where("genre.name").is(name)),
				group("genre.name"),
				project().and("_id").as("name").andExclude("_id")
		);

		return Optional.ofNullable(mongoOperations.aggregate(aggregation, Book.class, Genre.class).getUniqueMappedResult());
	}

	@Override
	public void updateName(Genre genre, String newName) {
		mongoOperations.updateMulti(
				query(where("genre.name").is(genre.getName())),
				update("genre", new Genre(newName)),
				Book.class
		);
	}
}
