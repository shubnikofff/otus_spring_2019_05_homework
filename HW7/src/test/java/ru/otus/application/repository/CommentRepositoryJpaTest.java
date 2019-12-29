package ru.otus.application.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;
import ru.otus.domain.repository.CommentRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Comment repository based on JPA")
@DataJpaTest
class CommentRepositoryJpaTest {
	private static final Long BOOK_ID = 1L;

	@Autowired
	private CommentRepository repository;

	@Autowired
	private TestEntityManager entityManager;

	@DisplayName("should return all comments by given book id")
	@Test
	void findAllByBookId() {
		val comments = entityManager.find(Book.class, BOOK_ID).getComments();

		assertThat(repository.findAllByBookId(BOOK_ID)).isEqualTo(comments);
	}

	@DisplayName("should delete comments by book id")
	@Test
	void deleteByBookId() {
		val comments = entityManager.find(Book.class, BOOK_ID).getComments();
		repository.deleteByBookId(BOOK_ID);

		comments.forEach(comment -> assertThat(entityManager.find(Comment.class, comment.getId())).isNull());
	}
}
