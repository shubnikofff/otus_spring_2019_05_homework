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
import ru.otus.domain.Genre;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan("ru.otus.repository")
@DisplayName("Genre repository")
class GenreRepositoryTest {

	private static final String FIRST_GENRE_NAME = "Genre#1";
	private static final String SECOND_GENRE_NAME = "Genre#2";

	@Autowired
	private GenreRepository repository;

	@Autowired
	private MongoOperations mongoOperations;

	private Genre firstGenre;

	private Genre secondGenre;

	@BeforeEach
	void setUp() {
		firstGenre = new Genre(FIRST_GENRE_NAME);
		secondGenre = new Genre(SECOND_GENRE_NAME);

		mongoOperations.dropCollection(Book.class);
		mongoOperations.insert(new Book("Book #1", firstGenre, Collections.emptyList()));
		mongoOperations.insert(new Book("Book #2", firstGenre, Collections.emptyList()));
		mongoOperations.insert(new Book("Book #3", secondGenre, Collections.emptyList()));
	}

	@Test
	@DisplayName("should find all genres")
	void findAll() {
		val result = repository.findAll();

		assertThat(result).hasSize(2);
		assertThat(result).contains(firstGenre, secondGenre);
	}

	@Test
	@DisplayName("should find genre by name")
	void findByName() {
		val result = repository.findByName(FIRST_GENRE_NAME);

		assertThat(result).isNotEmpty();
		assertThat(result).contains(firstGenre);
	}

	@Test
	@DisplayName("should not find genre by name")
	void findByName_NegativeScenario() {
		val result = repository.findByName("undefined");

		assertThat(result).isEmpty();
	}

	@Test
	@DisplayName("should update name of genre")
	void updateName() {
		val name = "New name";
		repository.updateName(firstGenre, name);
		val result = repository.findByName(name);

		assertThat(result).isNotEmpty();
		assertThat(result).contains(new Genre(name));
	}
}
