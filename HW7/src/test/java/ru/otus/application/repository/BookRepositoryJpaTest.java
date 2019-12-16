package ru.otus.application.repository;

import lombok.val;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;
import ru.otus.domain.model.Genre;
import ru.otus.domain.repository.BookRepository;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Book repository based on JPA")
@DataJpaTest
class BookRepositoryJpaTest {
	private static final int BOOKS_INITIAL_QUANTITY = 6;
	private static final Long FIRST_BOOK_ID = 1L;
	private static final Long FIRST_AUTHOR_ID = 1L;
	private static final String FIRST_AUTHOR_NAME = "Author #1";
	private static final String BOOK_TITLE = "Title";
	private static final String FIRST_BOOK_TITLE = "Book #1";
	private static final Long FIRST_GENRE_ID = 1L;
	private static final String FIRST_GENRE_NAME = "Genre #1";

	@Autowired
	private BookRepository repository;

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
	}

	@Test
	@DisplayName("should find book by id")
	void findById() {
		val book = repository.findById(FIRST_BOOK_ID);
		val expectedBook = entityManager.find(Book.class, FIRST_BOOK_ID);

		assertThat(book).isPresent().get().isEqualToComparingFieldByField(expectedBook);
	}

	@DisplayName("should save new book")
	@Test
	void saveNewBook() {
		val newBook = Book.builder()
				.title(BOOK_TITLE)
				.genre(entityManager.find(Genre.class, FIRST_GENRE_ID))
				.authors(Collections.singletonList(entityManager.find(Author.class, FIRST_AUTHOR_ID)))
				.build();

		repository.save(newBook);
		assertThat(newBook.getId()).isNotNull();

		val actualBook = entityManager.find(Book.class, newBook.getId());
		assertThat(actualBook).isNotNull()
				.matches(book -> book.getTitle().equals(BOOK_TITLE))
				.matches(book -> book.getGenre().getName().equals(FIRST_GENRE_NAME))
				.matches(book -> book.getAuthors().size() == 1)
				.matches(book -> book.getAuthors().get(0).getName().equals(FIRST_AUTHOR_NAME));
	}

	@DisplayName("should throw DataIntegrityViolationException when save with existing title")
	@Test
	void saveWithExistingTitle() {
		val book = Book.builder()
				.title(FIRST_BOOK_TITLE)
				.genre(entityManager.find(Genre.class, FIRST_GENRE_ID))
				.build();

		assertThatThrownBy(() -> repository.save(book))
				.isInstanceOf(DataIntegrityViolationException.class)
				.hasCauseInstanceOf(ConstraintViolationException.class);
	}

	@DisplayName("should throw DataIntegrityViolationException when save without title")
	@Test
	void saveWithoutTitle() {
		val book = Book.builder().genre(entityManager.find(Genre.class, FIRST_GENRE_ID)).build();

		assertThatThrownBy(() -> repository.save(book))
				.isInstanceOf(DataIntegrityViolationException.class)
				.hasCauseInstanceOf(ConstraintViolationException.class);
	}

	@DisplayName("should throw DataIntegrityViolationException when save without genre")
	@Test
	void saveWithoutGenre() {
		val book = Book.builder().title(FIRST_BOOK_TITLE).build();

		assertThatThrownBy(() -> repository.save(book))
				.isInstanceOf(DataIntegrityViolationException.class)
				.hasCauseInstanceOf(ConstraintViolationException.class);
	}

	@DisplayName("should delete book by id")
	@Test
	void deleteById() {
		val comments = entityManager.find(Book.class, FIRST_BOOK_ID).getComments();

		repository.deleteById(FIRST_BOOK_ID);

		assertThat(entityManager.find(Book.class, FIRST_BOOK_ID)).isNull();
		comments.forEach(comment -> assertThat(entityManager.find(Comment.class, comment.getId())).isNull());
	}
}
