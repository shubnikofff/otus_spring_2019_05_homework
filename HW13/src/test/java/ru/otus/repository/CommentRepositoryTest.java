package ru.otus.repository;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.repository.CommentRepository;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan("ru.otus.repository")
@DisplayName("Comment repository")
class CommentRepositoryTest {

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private CommentRepository repository;

	private Book book;

	private Comment comment;

	@BeforeEach
	void setUp() {
		mongoOperations.dropCollection(Book.class);
		mongoOperations.dropCollection(Comment.class);
		book = mongoOperations.insert(new Book("Title", new Genre("Genre"), Collections.emptyList()));
		comment = mongoOperations.insert(new Comment("User", "Text", book));
	}

	@Test
	@DisplayName("should find comments by book id")
	void findByBookId() {
		val result = repository.findByBookId(book.getId());

		assertThat(result).hasSize(1);
		assertThat(result).contains(comment);
	}

	@Test
	@DisplayName("should not find comments by book id")
	void findByBookId_NegativeScenario() {
		val result = repository.findByBookId("undefined");

		assertThat(result).isEmpty();
	}
}
