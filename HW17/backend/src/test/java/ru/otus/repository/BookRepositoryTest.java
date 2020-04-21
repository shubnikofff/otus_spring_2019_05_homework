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

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan("ru.otus.repository")
@DisplayName("Book repository")
class BookRepositoryTest {

	private static final String TITLE = "Title";
	private static final String GENRE_NAME = "Genre";
	private static final String AUTHOR_NAME = "Author";
	private static final String UNDEFINED = "undefined";

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private BookRepository repository;

	private Book book;

	@BeforeEach
	void setUp() {
		mongoOperations.dropCollection(Book.class);
		book = mongoOperations.insert(new Book(TITLE, new Genre(GENRE_NAME), singletonList(new Author(AUTHOR_NAME))));
	}

	@Test
	@DisplayName("should find book by title")
	void findByTitle() {
		val result = repository.findByTitle(TITLE);

		assertThat(result).isNotEmpty();
		assertThat(result).contains(book);
	}

	@Test
	@DisplayName("should not find book by title")
	void findByTitle_NegativeScenario() {
		val result = repository.findByTitle(UNDEFINED);

		assertThat(result).isEmpty();
	}

	@Test
	@DisplayName("should find books by genre name")
	void findByGenreName() {
		val result = repository.findByGenreName(GENRE_NAME);

		assertThat(result).hasSize(1);
		assertThat(result).contains(book);
	}

	@Test
	@DisplayName("should not find books by genre name")
	void findByGenreName_NegativeScenario() {
		val result = repository.findByGenreName(UNDEFINED);

		assertThat(result).isEmpty();
	}

	@Test
	@DisplayName("should find books by author name")
	void findByAuthorName() {
		val result = repository.findByAuthorName(AUTHOR_NAME);

		assertThat(result).hasSize(1);
		assertThat(result).contains(book);
	}

	@Test
	@DisplayName("should not find books by author name")
	void findByAuthorName_NegativeScenario() {
		val result = repository.findByAuthorName(UNDEFINED);

		assertThat(result).isEmpty();
	}
}
