package ru.otus.application.repository;

import lombok.val;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import ru.otus.domain.model.Author;
import ru.otus.domain.repository.AuthorRepository;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Author repository based on JPA")
@DataJpaTest
class AuthorRepositoryJpaTest {
	private static final int AUTHOR_INITIAL_QUANTITY = 8;
	private static final Long FIRST_AUTHOR_ID = 1L;
	private static final Long SECOND_AUTHOR_ID = 2L;
	private static final String FIRST_AUTHOR_NAME = "Author #1";
	private static final String SECOND_AUTHOR_NAME = "Author #2";
	private static final String NEW_AUTHOR_NAME = "New Author";
	private static final String UPDATED_AUTHOR_NAME = "Updated Author";

	@Autowired
	private AuthorRepository repository;

	@Autowired
	private TestEntityManager entityManager;

	@DisplayName("should return all authors")
	@Test
	void findAll() {
		val allAuthors = repository.findAll();
		assertThat(allAuthors).isNotNull().hasSize(AUTHOR_INITIAL_QUANTITY);
	}

	@DisplayName("should find authors by given name list")
	@Test
	void findByNameIn() {
		val authorList = repository.findByNameIn(Arrays.asList(FIRST_AUTHOR_NAME, SECOND_AUTHOR_NAME));
		val expectedAuthorList = Arrays.asList(
				entityManager.find(Author.class, FIRST_AUTHOR_ID),
				entityManager.find(Author.class, SECOND_AUTHOR_ID)
		);

		assertThat(authorList).isEqualTo(expectedAuthorList);
	}

	@DisplayName("should save new author")
	@Test
	void saveNewAuthor() {
		val newAuthor = repository.save(Author.builder().name(NEW_AUTHOR_NAME).build());
		assertThat(newAuthor.getId()).isNotNull();

		val foundAuthor = entityManager.find(Author.class, newAuthor.getId());
		assertThat(foundAuthor.getName()).isEqualTo(NEW_AUTHOR_NAME);
	}

	@DisplayName("should save existing author")
	@Test
	void saveExistingAuthor() {
		val author = repository.save(Author.builder().id(1L).name(UPDATED_AUTHOR_NAME).build());
		val foundAuthor = entityManager.find(Author.class, author.getId());

		assertThat(foundAuthor.getName()).isEqualTo(UPDATED_AUTHOR_NAME);
	}

	@DisplayName("should throw DataIntegrityViolationException when save without name")
	@Test
	void saveWithoutName() {
		assertThatThrownBy(() -> repository.save(Author.builder().build()))
				.isInstanceOf(DataIntegrityViolationException.class)
				.hasCauseInstanceOf(ConstraintViolationException.class);
	}

	@DisplayName("should throw DataIntegrityViolationException when save with existing name")
	@Test
	void saveWithExistingName() {
		assertThatThrownBy(() -> repository.save(Author.builder().name(FIRST_AUTHOR_NAME).build()))
				.isInstanceOf(DataIntegrityViolationException.class)
				.hasCauseInstanceOf(ConstraintViolationException.class);
	}

	@DisplayName("should remove author")
	@Test
	void deleteById() {
		repository.deleteById(FIRST_AUTHOR_ID);
		assertThat(entityManager.find(Author.class, FIRST_AUTHOR_ID)).isNull();
	}
}
