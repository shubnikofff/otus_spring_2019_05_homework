package ru.otus.application.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.model.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Author repository based on JPA")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {
	private static final int AUTHOR_INITIAL_QUANTITY = 8;
	private static final long DELETED_AUTHOR_ID = 1L;
	private static final String NEW_AUTHOR_NAME = "New Author";
	private static final String UPDATED_AUTHOR_NAME = "Updated Author";

	@Autowired
	private AuthorRepositoryJpa repository;

	@Autowired
	private TestEntityManager entityManager;

	@DisplayName("should return all authors")
	@Test
	void findAll() {
		val allAuthors = repository.findAll();
		assertThat(allAuthors).isNotNull().hasSize(AUTHOR_INITIAL_QUANTITY);
	}

	@DisplayName("should save new author")
	@Test
	void saveNewAuthor() {
		val newAuthor = repository.save(new Author(0, NEW_AUTHOR_NAME));
		assertThat(newAuthor.getId()).isGreaterThan(0);

		val foundAuthor = entityManager.find(Author.class, newAuthor.getId());
		assertThat(foundAuthor.getName()).isEqualTo(NEW_AUTHOR_NAME);
	}

	@DisplayName("should save existing author")
	@Test
	void saveExistingAuthor() {
		val newAuthor = repository.save(new Author(1L, UPDATED_AUTHOR_NAME));

		val foundAuthor = entityManager.find(Author.class, newAuthor.getId());
		assertThat(foundAuthor.getName()).isEqualTo(UPDATED_AUTHOR_NAME);
	}

	@DisplayName("should remove author")
	@Test
	void remove() {
		val author = entityManager.find(Author.class, DELETED_AUTHOR_ID);
		repository.remove(author);

		assertThat(entityManager.find(Author.class, DELETED_AUTHOR_ID)).isNull();
	}
}