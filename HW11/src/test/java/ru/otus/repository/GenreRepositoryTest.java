package ru.otus.repository;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;

import java.util.Collections;

import static java.util.Arrays.asList;

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

		val bookList = asList(
				new Book("Book#1", firstGenre, Collections.emptyList()),
				new Book("Book#2", firstGenre, Collections.emptyList()),
				new Book("Book#3", secondGenre, Collections.emptyList())
		);

		mongoOperations
				.dropCollection(Book.class)
				.thenMany(Flux.fromIterable(bookList))
				.flatMap(mongoOperations::insert)
				.doOnNext(book -> System.out.println("Inserted book: " + book))
				.blockLast();
	}

	@Test
	@DisplayName("should find all genres")
	void findAll() {
		StepVerifier
				.create(repository.findAll())
				.expectNext(firstGenre)
				.expectNext(secondGenre)
				.expectComplete()
				.verify();
	}

	@Test
	@DisplayName("should find genre by name")
	void findByName() {
		StepVerifier
				.create(repository.findByName(FIRST_GENRE_NAME))
				.expectNext(firstGenre)
				.expectComplete()
				.verify();
	}

	@Test
	@DisplayName("should complete if not found")
	void findByName_NotFound() {
		StepVerifier
				.create(repository.findByName("undefined"))
				.expectComplete()
				.verify();
	}

	@Test
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
				.expectComplete()
				.verify();
	}
}
