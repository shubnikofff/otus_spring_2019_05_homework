package ru.otus.application.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Genre repository based on JPA")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {
	private static final int GENRE_INITIAL_QUANTITY = 6;
	private static final String NEW_GENRE_NAME = "New Genre";
	private static final String UPDATED_GENRE_NAME = "Updated Genre";
	private static final long FIRST_GENRE_ID = 1L;
	private static final String FIRST_GENRE_NAME = "Genre #1";

	@Autowired
	private GenreRepositoryJpa repository;

	@Autowired
	private TestEntityManager entityManager;

	@DisplayName("should return all genres")
	@Test
	void findAll() {
		val allGenres = repository.findAll();
		assertThat(allGenres).isNotNull().hasSize(GENRE_INITIAL_QUANTITY);
	}

	@DisplayName("should insert genre")
	@Test
	void saveNewGenre() {
		val genre = new Genre(0, NEW_GENRE_NAME);
		val savedGenre = repository.save(genre);
		assertThat(savedGenre.getId()).isGreaterThan(0);

		val foundGenre = entityManager.find(Genre.class, savedGenre.getId());
		assertThat(foundGenre.getName()).isEqualTo(NEW_GENRE_NAME);
	}

	@DisplayName("should update genre")
	@Test
	void saveExistingGenre() {
		val genre = new Genre(1L, UPDATED_GENRE_NAME);
		val savedGenre = repository.save(genre);

		val foundGenre = entityManager.find(Genre.class, savedGenre.getId());
		assertThat(foundGenre.getName()).isEqualTo(UPDATED_GENRE_NAME);
	}

	@DisplayName("should remove genre")
	@Test
	void remove() {
		val genre = entityManager.find(Genre.class, FIRST_GENRE_ID);
		repository.remove(genre);

		assertThat(entityManager.find(Genre.class, FIRST_GENRE_ID)).isNull();
	}

	@DisplayName("should find genre by name")
	@Test
	void findByName() {
		val foundGenre = repository.findByName(FIRST_GENRE_NAME);
		val expectedGenre = entityManager.find(Genre.class, FIRST_GENRE_ID);

		assertThat(foundGenre).isPresent().get().isEqualToComparingFieldByField(expectedGenre);
	}
}
