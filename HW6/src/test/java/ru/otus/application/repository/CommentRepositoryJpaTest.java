package ru.otus.application.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Comment repository based on JPA")
@DataJpaTest
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {
	private static final Long BOOK_ID = 1L;
	private static final Long FIRST_COMMENT_ID = 1L;
	private static final Long SECOND_COMMENT_ID = 2L;
	private static final Long THIRD_COMMENT_ID = 3L;
	private static final String NEW_COMMENT_NAME = "New name";
	private static final String NEW_COMMENT_TEXT = "New comment";

	@Autowired
	private CommentRepositoryJpa repository;

	@Autowired
	private TestEntityManager entityManager;

	@DisplayName("should return all comments by given book id")
	@Test
	void findAll() {
		val comments = repository.findAllByBookId(BOOK_ID);
		val expectedComments = Arrays.asList(
				entityManager.find(Comment.class, FIRST_COMMENT_ID),
				entityManager.find(Comment.class, SECOND_COMMENT_ID),
				entityManager.find(Comment.class, THIRD_COMMENT_ID)
		);

		assertThat(comments).isEqualTo(expectedComments);
	}

	@DisplayName("should save comment")
	@Test
	void save() {
		val book = entityManager.find(Book.class, BOOK_ID);
		val comment = repository.save( new Comment(null, NEW_COMMENT_NAME, NEW_COMMENT_TEXT, book));
		val expectedComment = entityManager.find(Comment.class, comment.getId());

		assertThat(comment).isEqualToComparingFieldByField(expectedComment);
	}

	@DisplayName("should delete comments by book id")
	@Test
	void deleteByBookId() {
		repository.deleteByBookId(BOOK_ID);

		assertThat(entityManager.find(Comment.class, FIRST_COMMENT_ID)).isNull();
		assertThat(entityManager.find(Comment.class, SECOND_COMMENT_ID)).isNull();
		assertThat(entityManager.find(Comment.class, THIRD_COMMENT_ID)).isNull();
	}
}
