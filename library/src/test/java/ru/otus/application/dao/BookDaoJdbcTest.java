package ru.otus.application.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Book dao")
@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class})
class BookDaoJdbcTest {
	private static final int BOOKS_INITIAL_QUANTITY = 6;

	@Autowired
	private BookDaoJdbc daoJdbc;

	@DisplayName("should return all books")
	@Test
	void getAll() {
		val books = daoJdbc.getAll();
		assertThat(books).isNotNull().hasSize(BOOKS_INITIAL_QUANTITY)
				.allMatch(book -> book.getId() != null)
				.allMatch(book -> !book.getTitle().isEmpty())
				.allMatch(book -> book.getAuthors() != null && !book.getAuthors().isEmpty())
				.allMatch(book -> book.getGenre() != null);
	}

	@DisplayName("should return book by id")
	@Test
	void getById() {
		val book = daoJdbc.getById(6L);
		assertThat(book.getId()).isEqualTo(6L);
		assertThat(book.getTitle()).isEqualTo("Book #6");
		assertThat(book.getGenre()).isEqualTo(new Genre(6L, "Genre #6"));
		assertThat(book.getAuthors()).hasSize(2);
		assertThat(book.getAuthors().get(0)).isEqualTo(new Author(6L, "Author #6"));
		assertThat(book.getAuthors().get(1)).isEqualTo(new Author(7L, "Author #7"));
	}

	@Test
	void save() {
	}

	@DisplayName("should delete book by id")
	@Test
	void deleteById() {
		val quantityBeforeDelete = daoJdbc.getAll().size();
		daoJdbc.deleteById(1L);
		val quantityAfterDelete = daoJdbc.getAll().size();
		assertThat(quantityBeforeDelete - quantityAfterDelete).isEqualTo(1);
	}
}
