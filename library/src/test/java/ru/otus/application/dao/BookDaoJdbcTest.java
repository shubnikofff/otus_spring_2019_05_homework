package ru.otus.application.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

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

	@Test
	void getById() {
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
