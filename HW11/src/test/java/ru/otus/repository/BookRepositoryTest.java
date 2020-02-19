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
import ru.otus.domain.model.Comment;
import ru.otus.domain.model.Genre;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.configuration", "ru.otus.repository"})
@DisplayName("Book repository")
class BookRepositoryTest {

	private static final String TITLE = "Title";
	private static final String GENRE_NAME = "Genre";
	private static final String AUTHOR_NAME = "Author";

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private CommentRepository commentRepository;

	private Book book;

	@BeforeEach
	void setBook() {
		book = new Book(TITLE, new Genre(GENRE_NAME), Collections.singletonList(new Author(AUTHOR_NAME)));
	}

	@Test
	@DisplayName("should find book by title")
	@DirtiesContext
	void findByTitle() {
		bookRepository.save(book).subscribe();

		StepVerifier
				.create(bookRepository.findByTitle(TITLE))
				.expectNextCount(1)
				.expectComplete()
				.verify();
	}

	@Test
	@DisplayName("should find book by genre name")
	@DirtiesContext
	void findByGenreName() {
		bookRepository.save(book).subscribe();

		StepVerifier.create(bookRepository.findByGenreName(GENRE_NAME))
				.expectNextCount(1)
				.expectComplete()
				.verify();
	}

	@Test
	@DisplayName("should find book by author name")
	@DirtiesContext
	void findByAuthorName() {
		bookRepository.save(book).subscribe();

		StepVerifier
				.create(bookRepository.findByAuthorName(AUTHOR_NAME))
				.expectNextCount(1)
				.expectComplete()
				.verify();
	}

	@Test
	@DisplayName("should set id on save")
	@DirtiesContext
	void save() {
		StepVerifier.create(bookRepository.save(book))
				.assertNext(book -> assertThat(book.getId()).isNotEmpty())
				.expectComplete()
				.verify();
	}

	@Test
	@DisplayName("should delete all comments on delete book")
	@DirtiesContext
	void delete() {
		bookRepository.save(book).block();
		commentRepository.save(new Comment("User", "Text", book.getId())).block();
		bookRepository.delete(book).block();

		StepVerifier.create(bookRepository.findById(book.getId()))
				.expectNextCount(0)
				.expectComplete()
				.verify();

		StepVerifier.create(commentRepository.findByBookId(book.getId()))
				.expectNextCount(0)
				.expectComplete()
				.verify();
	}
}
