package ru.otus.domain.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.domain.model.Book;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.configuration", "ru.otus.domain.repository"})
@DisplayName("CommentRepository")
class CommentRepositoryTest {

	@Autowired
	private MongoOperations mongoOperations;

	@Test
	@DisplayName("should find comments by book id")
	void findByBookId() {
		val bookId = mongoOperations.findAll(Book.class).get(0).getId();


	}
}
