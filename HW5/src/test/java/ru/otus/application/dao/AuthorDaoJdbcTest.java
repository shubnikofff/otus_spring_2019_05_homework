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

import java.util.Arrays;
import java.util.HashSet;

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

	@DisplayName("should return all authors by given set of names")
	@Test
	void findAllByNameSet() {
		val authors = daoJdbc.findAllByNameSet(new HashSet<>(Arrays.asList("Author #1", "Author #2", "Other author")));
		assertThat(authors).hasSize(2);
		assertThat(authors.stream().map(Author::getName)).doesNotContain("Other author");
	}

	@DisplayName("should return list of id by given set of names")
	@Test
	void findAllIdsByNameSet() {
		val authorIds = daoJdbc.findAllIdsByNameSet(new HashSet<>(Arrays.asList("Author #1", "Author #2", "Other author")));
		assertThat(authorIds).hasSize(2);
		assertThat(authorIds).isEqualTo(Arrays.asList(1L, 2L));
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

	@DisplayName("should return authors by book id")
	@Test
	void findByBookId() {
		val authors = daoJdbc.findByBookId(6L);
		assertThat(authors).hasSize(2);
		assertThat(authors.get(0).getId()).isEqualTo(6L);
		assertThat(authors.get(1).getId()).isEqualTo(7L);
	}

	@DisplayName("should return authors who have books")
	@Test
	void findAllUsed() {
		assertThat(daoJdbc.findAllUsed()).isNotNull().doesNotContain(new Author(8L, "Author #8"));
	}

	@DisplayName("should insert new author")
	@Test
	void insert() {
		val id = daoJdbc.insert(new Author(null, "New author"));
		assertThat(id).isEqualTo(9L);
		assertThat(daoJdbc.findAll()).hasSize(AUTHOR_INITIAL_QUANTITY + 1);
		Assertions.assertThrows(DuplicateKeyException.class, () -> daoJdbc.insert(new Author(null, "New author")));
	}

	@DisplayName("should insert all authors from given list")
	@Test
	void insertAll() {
		val insertedRowQuantity = daoJdbc.insertAll(Arrays.asList(new Author(null, "Author #9"), new Author(null, "Author #10")));
		val authors = daoJdbc.findAll();
		assertThat(insertedRowQuantity).isEqualTo(2);
		assertThat(authors).hasSize(AUTHOR_INITIAL_QUANTITY + 2);
	}

	@DisplayName("should update author")
	@Test
	void update() {
		daoJdbc.update(new Author(1L, "Updated author"));
		assertThat(daoJdbc.findById(1L).getName()).isEqualTo("Updated author");
		Assertions.assertThrows(DuplicateKeyException.class, () -> daoJdbc.update(new Author(2L, "Updated author")));
	}

	@DisplayName("should delete author by id")
	@Test
	void deleteById() {
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> daoJdbc.deleteById(1L));

		daoJdbc.deleteById(8L);
		assertThat(daoJdbc.findAll()).hasSize(AUTHOR_INITIAL_QUANTITY - 1);
	}
}
