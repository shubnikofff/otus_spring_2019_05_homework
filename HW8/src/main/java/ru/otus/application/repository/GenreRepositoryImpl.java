package ru.otus.application.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryImpl implements GenreRepository {

	private final MongoOperations mongoOperations;

	@Override
	public List<Genre> findAll() {
		return mongoOperations.findDistinct("genre", Book.class, Genre.class);
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
