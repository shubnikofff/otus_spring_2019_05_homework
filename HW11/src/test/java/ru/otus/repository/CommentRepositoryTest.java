package ru.otus.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;

import java.util.Collections;
import java.util.Objects;

@DataMongoTest
class CommentRepositoryTest {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Test
	@DisplayName("should find comments by book id")
	void findByBookId() {
		val book = bookRepository.save(new Book("Title", null, Collections.emptyList())).block();
		val expectedComment = commentRepository.save(new Comment("user", "title", book)).block();

		StepVerifier
				.create(commentRepository.findByBookId(Objects.requireNonNull(book).getId()))
				.expectNext(Objects.requireNonNull(expectedComment))
				.expectComplete()
				.verify();
	}
}
