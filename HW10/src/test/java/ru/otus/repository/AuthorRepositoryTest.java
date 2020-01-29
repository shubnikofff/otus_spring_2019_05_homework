package ru.otus.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;

import java.util.HashSet;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.configuration", "ru.otus.repository"})
@DisplayName("Author repository")
class AuthorRepositoryTest {

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private BookRepository bookRepository;

	@Test
	@DisplayName("should find all authors")
	void findAll() {
		val authors = authorRepository.findAll();

		assertThat(authors).isNotEmpty();
		assertThat(authors.size()).isEqualTo(new HashSet<>(authors).size());
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	@DisplayName("should find author by name")
	void findByName() {
		val authorName = "Author";
		val expectedAuthor = new Author(authorName);
		bookRepository.save(new Book("Title", new Genre("genre"), singletonList(expectedAuthor)));
		val author = authorRepository.findByName(authorName);

		assertThat(author).isNotEmpty();
		assertThat(author).isEqualTo(Optional.of(expectedAuthor));
	}
}
