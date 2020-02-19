package ru.otus.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.configuration", "ru.otus.repository"})
@DisplayName("Genre repository")
class GenreRepositoryTest {

	private static final String FIRST_GENRE_NAME = "Genre #1";
	private static final String NEW_GENRE_NAME = "New Genre";

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private GenreRepository genreRepository;

	@Test
	@DisplayName("should find all unique genres")
	void findAll() {
		val genres = genreRepository.findAll();

		assertThat(genres).isNotEmpty();
		assertThat(genres.size()).isEqualTo(new HashSet<>(genres).size());
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	@DisplayName("should find genre by name")
	void findByName() {
		val expectedGenre = new Genre(NEW_GENRE_NAME);
		bookRepository.save(new Book("Title", expectedGenre, emptyList()));
		val genre = genreRepository.findByName(NEW_GENRE_NAME);

		assertThat(genre).isNotEmpty();
		assertThat(genre).isEqualTo(Optional.of(expectedGenre));
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	@DisplayName("should update name for given genre")
	void updateName() {
		val books = bookRepository.findByGenreName(FIRST_GENRE_NAME);
		genreRepository.updateName(new Genre(FIRST_GENRE_NAME), NEW_GENRE_NAME);
		val expectedBooks = bookRepository.findByGenreName(NEW_GENRE_NAME);

		assertThat(getModelIds(books, Book::getId)).isEqualTo(getModelIds(expectedBooks, Book::getId));
	}

	private <T, R> List<R> getModelIds(List<T> models, Function<T, R> mapper) {
		return models.stream().map(mapper).collect(Collectors.toList());
	}
}
