package ru.otus.application.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.domain.model.Genre;
import ru.otus.domain.repository.GenreRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Genre repository based on JPA")
@DataJpaTest
class GenreRepositoryJpaTest {
	private static final int GENRE_INITIAL_QUANTITY = 6;
	private static final String NEW_GENRE_NAME = "New Genre";
	private static final String UPDATED_GENRE_NAME = "Updated Genre";
	private static final Long FIRST_GENRE_ID = 1L;
	private static final String FIRST_GENRE_NAME = "Genre #1";

	@Autowired
	private GenreRepository repository;

	@Autowired
	private TestEntityManager entityManager;

	@DisplayName("should return all genres")
	@Test
	void findAll() {
		val allGenres = repository.findAll();
		assertThat(allGenres).isNotNull().hasSize(GENRE_INITIAL_QUANTITY);
	}

	@DisplayName("should find genre by name")
	@Test
	void findByName() {
		val genre = repository.findByName(FIRST_GENRE_NAME);
		assertThat(genre).isPresent().hasValue(entityManager.find(Genre.class, FIRST_GENRE_ID));

		assertThat(repository.findByName("No genre")).isNotPresent();
	}

	@DisplayName("should save new genre")
	@Test
	void saveNewGenre() {
		val genre = repository.save(Genre.builder().name(NEW_GENRE_NAME).build());
		assertThat(genre.getId()).isNotNull();

		val savedGenre = entityManager.find(Genre.class, genre.getId());
		assertThat(savedGenre.getName()).isEqualTo(NEW_GENRE_NAME);
	}

	@DisplayName("should update genre")
	@Test
	void saveExistingGenre() {
		val genre = entityManager.find(Genre.class, FIRST_GENRE_ID);
		genre.setName(UPDATED_GENRE_NAME);
		repository.save(genre);

		val savedGenre = entityManager.find(Genre.class, FIRST_GENRE_ID);
		assertThat(savedGenre.getName()).isEqualTo(UPDATED_GENRE_NAME);
	}

	@DisplayName("should remove genre")
	@Test
	void delete() {
		val genre = entityManager.find(Genre.class, FIRST_GENRE_ID);
		repository.delete(genre);

		assertThat(entityManager.find(Genre.class, FIRST_GENRE_ID)).isNull();
	}
}
