package ru.otus.application.dao;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import ru.otus.domain.model.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Author dao")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {
	private static final int AUTHOR_INITIAL_QUANTITY = 8;

	@Autowired
	private AuthorDaoJdbc daoJdbc;

	@DisplayName("should return all authors")
	@Test
	void findAll() {
		assertThat(daoJdbc.findAll()).isNotNull().hasSize(AUTHOR_INITIAL_QUANTITY);
	}

	@DisplayName("should return author by id")
	@Test
	void findById() {
		val author = daoJdbc.findById(1L);
		assertThat(author.getName()).isEqualTo("Author #1");
	}

	@DisplayName("should return author by name")
	@Test
	void findByName() {
		val author = daoJdbc.findByName("Author #1");
		assertThat(author.getId()).isEqualTo(1L);
	}

	@DisplayName("should return authors who have books")
	@Test
	void getUsed() {
		assertThat(daoJdbc.getUsed()).isNotNull().doesNotContain(new Author(8L, "Author #8"));
	}

	@DisplayName("should insert new author")
	@Test
	void insert() {
		Assertions.assertThrows(DuplicateKeyException.class, () -> daoJdbc.save(new Author(null, "Author #1")));

		daoJdbc.save(new Author(null, "New author"));
		val author = daoJdbc.findById((long) (AUTHOR_INITIAL_QUANTITY + 2));
		assertThat(author).isNotNull();
		assertThat(author.getName()).isEqualTo("New author");
	}

	@DisplayName("should update author")
	@Test
	void update() {
		Assertions.assertThrows(DuplicateKeyException.class, () -> daoJdbc.save(new Author(2L, "Author #1")));

		daoJdbc.save(new Author(2L, "Updated author"));
		val author = daoJdbc.findById(2L);
		assertThat(author).isNotNull();
		assertThat(author.getName()).isEqualTo("Updated author");
	}

	@DisplayName("should delete author by id")
	@Test
	void deleteById() {
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> daoJdbc.deleteById(1L));

		daoJdbc.deleteById(8L);
		assertThat(daoJdbc.findAll()).hasSize(AUTHOR_INITIAL_QUANTITY - 1);
	}
}
