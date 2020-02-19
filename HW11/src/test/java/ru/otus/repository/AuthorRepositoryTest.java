package ru.otus.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import reactor.test.StepVerifier;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;

import java.util.Arrays;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.configuration", "ru.otus.repository"})
@DisplayName("Author repository")
class AuthorRepositoryTest {

	private static final String FIRST_AUTHOR_NAME = "Author #1";
	private static final String SECOND_AUTHOR_NAME = "Author #2";

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private BookRepository bookRepository;

	private Author firstAuthor;

	private Author secondAuthor;

	@BeforeEach
	void setBook() {
		firstAuthor = new Author(FIRST_AUTHOR_NAME);
		secondAuthor = new Author(SECOND_AUTHOR_NAME);
		bookRepository
				.save(new Book("Title", new Genre("Genre"), Arrays.asList(firstAuthor, secondAuthor)))
				.subscribe();
	}

	@Test
	@DisplayName("should find all authors")
	@DirtiesContext
	void findAll() {
		StepVerifier
				.create(authorRepository.findAll())
				.expectNext(firstAuthor)
				.expectNext(secondAuthor)
				.expectComplete()
				.verify();
	}

	@Test
	@DisplayName("should find author by name")
	@DirtiesContext
	void findByName() {
		StepVerifier
				.create(authorRepository.findByName(SECOND_AUTHOR_NAME))
				.expectNext(secondAuthor)
				.expectComplete()
				.verify();
	}
}
