package ru.otus.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.annotation.DirtiesContext;
import reactor.test.StepVerifier;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;

import java.util.Collections;
import java.util.NoSuchElementException;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.repository"})
@DisplayName("Genre repository")
class GenreRepositoryTest {

	private static final String FIRST_GENRE_NAME = "Genre#1";
	private static final String SECOND_GENRE_NAME = "Genre#2";

	@Autowired
	private ReactiveMongoOperations mongoOperations;

	@Autowired
	private GenreRepository repository;

	private Genre firstGenre;

	private Genre secondGenre;

	@BeforeEach
	void setupDB() {
		firstGenre = new Genre(FIRST_GENRE_NAME);
		secondGenre = new Genre(SECOND_GENRE_NAME);

		mongoOperations.save(new Book("Book#1", firstGenre, Collections.emptyList())).block();
		mongoOperations.save(new Book("Book#2", firstGenre, Collections.emptyList())).block();
		mongoOperations.save(new Book("Book#3", secondGenre, Collections.emptyList())).block();
	}

	@Test
	@DisplayName("should find all genres")
	@DirtiesContext
	void findAll() {
		StepVerifier
				.create(repository.findAll())
				.expectNext(firstGenre)
				.expectNext(secondGenre)
				.expectComplete()
				.verify();
	}

	@Test
	@DirtiesContext
	@DisplayName("should find genre by name")
	void findByName() {
		StepVerifier
				.create(repository.findByName(FIRST_GENRE_NAME))
				.expectNext(firstGenre)
				.expectComplete()
				.verify();

		StepVerifier
				.create(repository.findByName("No existing genre"))
				.expectError(NoSuchElementException.class)
				.verify();
	}

	@Test
	@DirtiesContext
	@DisplayName("should update name of given genre")
	void updateName() {
		repository.updateName(firstGenre, "New Genre").block();

		StepVerifier
				.create(repository.findByName("New Genre"))
				.expectNextCount(1)
				.expectComplete()
				.verify();

		StepVerifier
				.create(repository.findByName(FIRST_GENRE_NAME))
				.expectError(NoSuchElementException.class)
				.verify();
	}
}
