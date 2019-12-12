package ru.otus.application.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;
import ru.otus.domain.repository.BookRepository;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Book repository based on JPA")
@DataJpaTest
class BookRepositoryJpaTest {
	private static final int BOOKS_INITIAL_QUANTITY = 6;
	private static final Long FIRST_BOOK_ID = 1L;
	private static final Long THIRD_BOOK_ID = 3L;
	private static final Long FIRST_AUTHOR_ID = 1L;
	private static final String FIRST_AUTHOR_NAME = "Author #1";
	private static final String BOOK_TITLE = "Title";
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

	@DisplayName("should save book")
	@Test
	void save() {
		val newBook = new Book(
			null,
			BOOK_TITLE,
			entityManager.find(Genre.class, FIRST_GENRE_ID),
			Collections.singletonList(entityManager.find(Author.class, FIRST_AUTHOR_ID))
		);

		repository.save(newBook);
		assertThat(newBook.getId()).isNotNull();

		val actualBook = entityManager.find(Book.class, newBook.getId());
		assertThat(actualBook).isNotNull()
				.matches(book -> book.getTitle().equals(BOOK_TITLE))
				.matches(book -> book.getGenre().getName().equals(FIRST_GENRE_NAME))
				.matches(book -> book.getAuthors().size() == 1)
				.matches(book -> book.getAuthors().get(0).getName().equals(FIRST_AUTHOR_NAME));
	}

	@DisplayName("should delete book by id")
	@Test
	void deleteById() {
		repository.deleteById(THIRD_BOOK_ID);
		assertThat(entityManager.find(Book.class, THIRD_BOOK_ID)).isNull();
	}

	@Test
	@DisplayName("should find book by id")
	void findById() {
		val book = repository.findById(FIRST_BOOK_ID);
		val expectedBook = entityManager.find(Book.class, FIRST_BOOK_ID);

		assertThat(book).isPresent().get().isEqualToComparingFieldByField(expectedBook);

	}
}
