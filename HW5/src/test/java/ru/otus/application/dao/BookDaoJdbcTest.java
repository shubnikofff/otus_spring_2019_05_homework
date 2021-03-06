package ru.otus.application.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Book dao")
@JdbcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
class BookDaoJdbcTest {
	private static final int BOOKS_INITIAL_QUANTITY = 6;

	@Autowired
	private BookDaoJdbc daoJdbc;

	@DisplayName("should return all books")
	@Test
	void findAll() {
		val books = daoJdbc.findAll();
		assertThat(books).isNotNull().hasSize(BOOKS_INITIAL_QUANTITY)
				.allMatch(book -> book.getId() != null)
				.allMatch(book -> !book.getTitle().isEmpty())
				.allMatch(book -> book.getAuthors() != null && !book.getAuthors().isEmpty())
				.allMatch(book -> book.getGenre() != null);
	}

	@DisplayName("should return book by id")
	@Test
	void findById() {
		val book = daoJdbc.findById(6L);
		assertThat(book.getId()).isEqualTo(6L);
		assertThat(book.getTitle()).isEqualTo("Book #6");
		assertThat(book.getGenre()).isEqualTo(new Genre(6L, "Genre #6"));
		assertThat(book.getAuthors()).hasSize(2);
		assertThat(book.getAuthors().get(0)).isEqualTo(new Author(6L, "Author #6"));
		assertThat(book.getAuthors().get(1)).isEqualTo(new Author(7L, "Author #7"));
	}

	@DisplayName("should insert book with new author and new genre")
	@Test
	void insertWithNewGenreAndNewAuthor() {
		val authorList = Arrays.asList(new Author(null, "Author #1"), new Author(null, "New Author"));
		val id = daoJdbc.insert(new Book(null, "New Book", authorList, new Genre(null, "New Genre")));
		val book = daoJdbc.findById(id);
		assertThat(book.getId()).isEqualTo(7L);
		assertThat(book.getTitle()).isEqualTo("New Book");
		assertThat(book.getGenre()).isEqualTo(new Genre(7L, "New Genre"));
		assertThat(book.getAuthors()).hasSize(2);
		assertThat(book.getAuthors().get(0)).isEqualTo(new Author(1L, "Author #1"));
		assertThat(book.getAuthors().get(1)).isEqualTo(new Author(9L, "New Author"));
	}

	@DisplayName("should insert book with new author and new genre")
	@Test
	void insertWithExistingGenreAndExistingAuthor() {
		val authorList = Arrays.asList(new Author(null, "Author #1"), new Author(null, "Author #2"));
		val id = daoJdbc.insert(new Book(null, "New Book", authorList, new Genre(null, "Genre #1")));
		val book = daoJdbc.findById(id);
		assertThat(book.getId()).isEqualTo(7L);
		assertThat(book.getTitle()).isEqualTo("New Book");
		assertThat(book.getGenre()).isEqualTo(new Genre(1L, "Genre #1"));
		assertThat(book.getAuthors()).hasSize(2);
		assertThat(book.getAuthors().get(0)).isEqualTo(new Author(1L, "Author #1"));
		assertThat(book.getAuthors().get(1)).isEqualTo(new Author(2L, "Author #2"));
	}

	@DisplayName("should update book with new author and new genre")
	@Test
	void updateWithNewGenreAndNewAuthor() {
		val authorList = Arrays.asList(new Author(1L, "Author #1"), new Author(null, "New Author"));
		val updatedRows = daoJdbc.update(new Book(1L, "Updated Book", authorList, new Genre(null, "New Genre")));
		assertThat(updatedRows).isEqualTo(1);

		val book = daoJdbc.findById(1L);
		assertThat(book.getTitle()).isEqualTo("Updated Book");
		assertThat(book.getGenre()).isEqualTo(new Genre(7L, "New Genre"));
		assertThat(book.getAuthors()).hasSize(2);
		assertThat(book.getAuthors().get(0)).isEqualTo(new Author(1L, "Author #1"));
		assertThat(book.getAuthors().get(1)).isEqualTo(new Author(9L, "New Author"));
	}

	@DisplayName("should insert book with new author and new genre")
	@Test
	void updateWithExistingGenreAndExistingAuthor() {
		val authorList = Arrays.asList(new Author(1L, "Author #1"), new Author(2L, "Author #2"));
		val updatedRows = daoJdbc.update(new Book(1L, "Updated Book", authorList, new Genre(1L, "Genre #1")));
		assertThat(updatedRows).isEqualTo(1);

		val book = daoJdbc.findById(1L);
		assertThat(book.getTitle()).isEqualTo("Updated Book");
		assertThat(book.getGenre()).isEqualTo(new Genre(1L, "Genre #1"));
		assertThat(book.getAuthors()).hasSize(2);
		assertThat(book.getAuthors().get(0)).isEqualTo(new Author(1L, "Author #1"));
		assertThat(book.getAuthors().get(1)).isEqualTo(new Author(2L, "Author #2"));
	}

	@DisplayName("should delete book by id")
	@Test
	void deleteById() {
		val quantityBeforeDelete = daoJdbc.findAll().size();
		daoJdbc.deleteById(1L);
		val quantityAfterDelete = daoJdbc.findAll().size();
		assertThat(quantityBeforeDelete - quantityAfterDelete).isEqualTo(1);
	}
}
