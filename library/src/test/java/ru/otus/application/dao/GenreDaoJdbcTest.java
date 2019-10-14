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
import ru.otus.domain.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Genre dao")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {
	private static final int GENRE_INITIAL_QUANTITY = 6;

	@Autowired
	private GenreDaoJdbc daoJdbc;

	@DisplayName("should return all genres")
	@Test
	void findAll() {
		val allGenres = daoJdbc.findAll();
		assertThat(allGenres).isNotNull().hasSize(GENRE_INITIAL_QUANTITY);
	}

	@DisplayName("should return genre by id")
	@Test
	void findById() {
		val genre = daoJdbc.findByById(1L);
		assertThat(genre.getName()).isEqualTo("Genre #1");
	}

	@DisplayName("should return genre by name")
	@Test
	void findByName() {
		val genre = daoJdbc.findByName("Genre #1");
		assertThat(genre.getId()).isEqualTo(1L);
	}

	@DisplayName("should insert new genre")
	@Test
	void insert() {
		Assertions.assertThrows(DuplicateKeyException.class, () -> daoJdbc.save(new Genre(null, "Genre #1")));

		daoJdbc.save(new Genre(null, "New genre"));
		val genre = daoJdbc.findByById((long) (GENRE_INITIAL_QUANTITY + 2));
		assertThat(genre).isNotNull();
		assertThat(genre.getName()).isEqualTo("New genre");
	}

	@DisplayName("should update genre")
	@Test
	void update() {
		Assertions.assertThrows(DuplicateKeyException.class, () -> daoJdbc.save(new Genre(2L, "Genre #1")));

		daoJdbc.save(new Genre(2L, "Updated genre"));
		val genre = daoJdbc.findByById(2L);
		assertThat(genre).isNotNull();
		assertThat(genre.getName()).isEqualTo("Updated genre");
	}

	@DisplayName("should delete genre by id")
	@Test
	void deleteById() {
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> daoJdbc.deleteById(1L));

		daoJdbc.deleteById(3L);
		assertThat(daoJdbc.findAll()).hasSize(GENRE_INITIAL_QUANTITY - 1);

	}
}
