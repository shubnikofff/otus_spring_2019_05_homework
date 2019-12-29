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

	@DisplayName("should find author by given name")
	@Test
	void findByName() {
		val author = repository.findByName(FIRST_AUTHOR_NAME);
		assertThat(author).isEqualTo(entityManager.find(Author.class, FIRST_AUTHOR_ID));

		val anonymous = repository.findByName("Anonymous");
		assertThat(anonymous).isNull();
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
		val author = repository.save(Author.builder().name(NEW_AUTHOR_NAME).build());
		assertThat(author.getId()).isNotNull();

		val savedAuthor = entityManager.find(Author.class, author.getId());
		assertThat(savedAuthor.getName()).isEqualTo(NEW_AUTHOR_NAME);
	}

	@DisplayName("should save existing author")
	@Test
	void saveExistingAuthor() {
		val author = entityManager.find(Author.class, FIRST_AUTHOR_ID);
		author.setName(UPDATED_AUTHOR_NAME);
		repository.save(author);

		val savedAuthor = entityManager.find(Author.class, FIRST_AUTHOR_ID);
		assertThat(savedAuthor.getName()).isEqualTo(UPDATED_AUTHOR_NAME);
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
	void delete() {
		val author = entityManager.find(Author.class, FIRST_AUTHOR_ID);
		repository.delete(author);

		assertThat(entityManager.find(Author.class, FIRST_AUTHOR_ID)).isNull();
	}
}
