package ru.otus.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.annotation.DirtiesContext;
import reactor.test.StepVerifier;
import ru.otus.domain.model.Comment;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.configuration", "ru.otus.repository"})
@DisplayName("Comment repository")
class CommentRepositoryTest {

	@Autowired
	private ReactiveMongoOperations mongoOperations;

	@Autowired
	private CommentRepository repository;

	@Test
	@DisplayName("should find comments by book id")
	@DirtiesContext
	void findByBookId() {
		val bookId = "bookId";
		mongoOperations.save(new Comment("user", "title", bookId)).block();

		StepVerifier
				.create(repository.findByBookId(bookId))
				.expectNextCount(1)
				.expectComplete()
				.verify();
	}
}
