package ru.otus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;

@Repository
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

	private final ReactiveMongoOperations mongoOperations;

	@Override
	public Flux<Book> findByAuthorName(String name) {
		return mongoOperations.find(Query.query(Criteria.where("authors").is(new Author(name))), Book.class);
	}
}
