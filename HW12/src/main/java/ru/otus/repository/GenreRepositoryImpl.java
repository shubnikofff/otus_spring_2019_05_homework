package ru.otus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Repository;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;

import java.util.List;
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
	public List<Genre> findAll() {
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
	public Genre updateName(Genre genre, String newName) {
		mongoOperations.updateMulti(
				query(where("genre.name").is(genre.getName())),
				update("genre", new Genre(newName)),
				Book.class
		);

		genre.setName(newName);
		return genre;
	}
}
