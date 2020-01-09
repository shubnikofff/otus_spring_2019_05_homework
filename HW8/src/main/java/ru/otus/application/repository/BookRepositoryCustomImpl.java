package ru.otus.application.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

	private final MongoOperations mongoOperations;

	@Override
	public List<Book> findByAuthorName(String name) {
		return mongoOperations.find(Query.query(Criteria.where("authors").is(new Author(name))), Book.class);
	}
}
