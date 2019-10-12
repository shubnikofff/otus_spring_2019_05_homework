package ru.otus.application.repository;

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

@DisplayName("Genre repository")
@JdbcTest
@Import(GenreRepositoryJdbc.class)
class GenreRepositoryJdbcTest {
	private static final int GENRE_INITIAL_QUANTITY = 6;

	@Autowired
	private GenreRepositoryJdbc genreRepository;

	@DisplayName("should return all genres")
	@Test
	void getAll() {
		val allGenres = genreRepository.getAll();
		assertThat(allGenres).isNotNull().hasSize(GENRE_INITIAL_QUANTITY);
	}

	@DisplayName("should get genre by id")
	@Test
	void getById() {
		val genre = genreRepository.getById(1L);
		assertThat(genre.getName()).isEqualTo("Genre #1");
	}

	@DisplayName("should save genre")
	@Test
	void insert() {
		Assertions.assertThrows(DuplicateKeyException.class, () -> genreRepository.save(new Genre(null, "Genre #1")));

		genreRepository.save(new Genre(null, "New genre"));
		val genre = genreRepository.getById((long) (GENRE_INITIAL_QUANTITY + 2));
		assertThat(genre).isNotNull();
		assertThat(genre.getName()).isEqualTo("New genre");
	}

	@DisplayName("should update genre")
	@Test
	void update() {
		Assertions.assertThrows(DuplicateKeyException.class, () -> genreRepository.save(new Genre(2L, "Genre #1")));

		genreRepository.save(new Genre(2L, "Updated genre"));
		val genre = genreRepository.getById(2L);
		assertThat(genre).isNotNull();
		assertThat(genre.getName()).isEqualTo("Updated genre");
	}

	@DisplayName("should delete genre by id")
	@Test
	void deleteById() {
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> genreRepository.deleteById(1L));

		genreRepository.deleteById(3L);
		assertThat(genreRepository.getAll()).hasSize(GENRE_INITIAL_QUANTITY - 1);

	}
}
