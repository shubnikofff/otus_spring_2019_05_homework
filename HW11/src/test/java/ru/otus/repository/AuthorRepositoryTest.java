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
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;

import java.util.Arrays;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.repository"})
@DisplayName("Author repository")
class AuthorRepositoryTest {

	private static final String FIRST_AUTHOR_NAME = "Author #1";
	private static final String SECOND_AUTHOR_NAME = "Author #2";

	@Autowired
	private ReactiveMongoOperations mongoOperations;

	@Autowired
	private AuthorRepository repository;

	private Author firstAuthor;

	private Author secondAuthor;

	@BeforeEach
	void setupDB() {
		firstAuthor = new Author(FIRST_AUTHOR_NAME);
		secondAuthor = new Author(SECOND_AUTHOR_NAME);

		mongoOperations.dropCollection(Book.class)
				.thenReturn(new Book("Title", new Genre("Genre"), Arrays.asList(firstAuthor, secondAuthor)))
				.flatMap(mongoOperations::insert)
				.doOnNext(book -> System.out.println("Inserted book: " + book))
				.block();
	}

	@Test
	@DisplayName("should find all authors")
	void findAll() {
		StepVerifier
				.create(repository.findAll())
				.expectNext(firstAuthor)
				.expectNext(secondAuthor)
				.expectComplete()
				.verify();
	}

	@Test
	@DisplayName("should find author by name")
	void findByName() {
		StepVerifier
				.create(repository.findByName(SECOND_AUTHOR_NAME))
				.expectNext(secondAuthor)
				.expectComplete()
				.verify();
	}

	@Test
	@DisplayName("should complete when author not found")
	void findByName_NotFound() {
		StepVerifier
				.create(repository.findByName("undefined"))
				.expectComplete()
				.verify();
	}
}
