package ru.otus.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.test.StepVerifier;
import ru.otus.domain.model.Comment;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.repository"})
@DisplayName("Comment repository")
class CommentRepositoryTest {

	private final static String BOOK_ID = "bookId";

	@Autowired
	private ReactiveMongoOperations mongoOperations;

	@Autowired
	private CommentRepository repository;

	@BeforeEach
	public void setUp() {
		mongoOperations
				.dropCollection(Comment.class)
				.thenReturn( new Comment("user", "title", BOOK_ID))
				.flatMap(mongoOperations::insert)
				.doOnNext(comment -> System.out.println("Inserted comment: " + comment))
				.block();
	}

	@Test
	@DisplayName("should find comments by book id")
	void findByBookId() {
		StepVerifier
				.create(repository.findByBookId(BOOK_ID))
				.expectNextCount(1)
				.expectComplete()
				.verify();
	}
}
