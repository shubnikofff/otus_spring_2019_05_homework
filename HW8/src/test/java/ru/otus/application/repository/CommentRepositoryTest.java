package ru.otus.application.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.configuration", "ru.otus.application.repository"})
@DisplayName("CommentRepository")
class CommentRepositoryTest {

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private CommentRepository commentRepository;

	@Test
	@DisplayName("should find comments by book id")
	void findByBookId() {
		val bookId = mongoOperations.findAll(Book.class).get(0).getId();
		val expectedComments = mongoOperations.find(query(where("bookId").is(bookId)), Comment.class);

		assertThat(commentRepository.findByBookId(bookId)).isEqualTo(expectedComments);
	}
}
