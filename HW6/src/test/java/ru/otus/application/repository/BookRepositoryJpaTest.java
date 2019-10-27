package ru.otus.application.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;
import ru.otus.domain.model.Genre;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Book repository based on JPA")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {
	private static final int BOOKS_INITIAL_QUANTITY = 6;
	private static final long DELETED_BOOK_ID = 1L;
	private static final String AUTHOR_NAME = "Author";
	private static final String BOOK_TITLE = "Title";
	private static final String GENRE_NAME = "Genre";
	private static final String COMMENT_NAME = "Name";
	private static final String COMMENT_TEXT = "Text";

	@Autowired
	private BookRepositoryJpa repository;

	@Autowired
	private TestEntityManager entityManager;

	@DisplayName("should return all books")
	@Test
	void findAll() {
		val books = repository.findAll();
		assertThat(books).hasSize(BOOKS_INITIAL_QUANTITY)
				.allMatch(book -> book.getId() > 0)
				.allMatch(book -> !book.getTitle().isEmpty())
				.allMatch(book -> book.getGenre() != null)
				.allMatch(book -> !book.getAuthors().isEmpty());

		assertThat(books.get(0).getComments()).hasSize(3);
	}

	@DisplayName("should save book")
	@Test
	void save() {
		val newBook = new Book(
			0,
			BOOK_TITLE,
			new Genre(0, GENRE_NAME),
			Collections.singletonList(new Author(0, AUTHOR_NAME)),
			Collections.singletonList(new Comment(0, COMMENT_NAME, COMMENT_TEXT))
		);

		repository.save(newBook);
		assertThat(newBook.getId()).isGreaterThan(0);

		val actualBook = entityManager.find(Book.class, newBook.getId());
		assertThat(actualBook).isNotNull()
				.matches(book -> book.getTitle().equals(BOOK_TITLE))
				.matches(book -> book.getGenre() != null)
				.matches(book -> book.getAuthors().size() == 1)
				.matches(book -> book.getComments().size() == 1);
	}

	@DisplayName("should remove book")
	@Test
	void remove() {
		val book = entityManager.find(Book.class, DELETED_BOOK_ID);
		repository.remove(book);

		assertThat(entityManager.find(Book.class, DELETED_BOOK_ID)).isNull();
	}
}
