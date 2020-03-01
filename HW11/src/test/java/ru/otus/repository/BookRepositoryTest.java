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
import ru.otus.domain.model.Comment;
import ru.otus.domain.model.Genre;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.repository"})
@DisplayName("Book repository")
class BookRepositoryTest {

	private static final String TITLE = "Title";
	private static final String GENRE_NAME = "Genre";
	private static final String AUTHOR_NAME = "Author";

	@Autowired
	private ReactiveMongoOperations mongoOperations;

	@Autowired
	private BookRepository repository;

	@Autowired
	private CommentRepository commentRepository;

	private Book book;

	@BeforeEach
	void setBook() {
		book = mongoOperations.dropCollection(Book.class)
				.thenReturn(new Book(TITLE, new Genre(GENRE_NAME), Collections.singletonList(new Author(AUTHOR_NAME))))
				.flatMap(mongoOperations::insert)
				.doOnNext(book -> System.out.println("Inserted book: " + book))
				.block();
	}

	@Test
	@DisplayName("should find book by title")
	void findByTitle() {
		StepVerifier
				.create(repository.findByTitle(TITLE))
				.expectNextCount(1)
				.expectComplete()
				.verify();
	}

	@Test
	@DisplayName("should find book by genre name")
	void findByGenreName() {
		StepVerifier.create(repository.findByGenreName(GENRE_NAME))
				.expectNextCount(1)
				.expectComplete()
				.verify();
	}

	@Test
	@DisplayName("should find book by author name")
	void findByAuthorName() {
		StepVerifier
				.create(repository.findByAuthorName(AUTHOR_NAME))
				.expectNextCount(1)
				.expectComplete()
				.verify();
	}

	@Test
	@DisplayName("should set id on save")
	void save() {
		StepVerifier.create(repository.save(book))
				.assertNext(book -> assertThat(book.getId()).isNotEmpty())
				.expectComplete()
				.verify();
	}

	@Test
	@DisplayName("should delete book with comments")
	void delete() {
		mongoOperations.save(new Comment("User", "Text", book.getId()))
				.flatMap(comment -> mongoOperations.remove(book))
				.block();

		StepVerifier.create(repository.findById(book.getId()))
				.expectNextCount(0)
				.expectComplete()
				.verify();

		StepVerifier.create(commentRepository.findByBookId(book.getId()))
				.expectNextCount(0)
				.expectComplete()
				.verify();
	}
}
