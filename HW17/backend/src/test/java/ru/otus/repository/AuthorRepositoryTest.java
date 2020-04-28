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
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan("ru.otus.repository")
@DisplayName("Author repository")
class AuthorRepositoryTest {

	private static final String FIRST_AUTHOR_NAME = "Author #1";
	private static final String SECOND_AUTHOR_NAME = "Author #2";

	@Autowired
	private AuthorRepository repository;

	@Autowired
	private MongoOperations mongoOperations;

	private Author firstAuthor;

	private Author secondAuthor;

	@BeforeEach
	void setUp() {
		firstAuthor = new Author(FIRST_AUTHOR_NAME);
		secondAuthor = new Author(SECOND_AUTHOR_NAME);

		mongoOperations.dropCollection(Book.class);
		mongoOperations.insert(new Book("Book #1", new Genre("Genre"), asList(firstAuthor, secondAuthor)));
		mongoOperations.insert(new Book("Book #2", new Genre("Genre"), singletonList(firstAuthor)));
		mongoOperations.insert(new Book("Book #3", new Genre("Genre"), singletonList(secondAuthor)));
	}

	@Test
	@DisplayName("should find all authors")
	void findAll() {
		val result = repository.findAll();

		assertThat(result).hasSize(2);
		assertThat(result).contains(firstAuthor, secondAuthor);
	}

	@Test
	@DisplayName("should find author by name")
	void findByName() {
		val result = repository.findByName(FIRST_AUTHOR_NAME);

		assertThat(result).isNotEmpty();
		assertThat(result).contains(firstAuthor);
	}

	@Test
	@DisplayName("should not find author by name")
	void findByName_NegativeScenario() {
		val result = repository.findByName("undefined");

		assertThat(result).isEmpty();
	}
}
