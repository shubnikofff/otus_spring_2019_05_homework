package ru.otus.repository.event;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.repository.BookRepository;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan("ru.otus.repository")
@DisplayName("Book repository event listener")
class BookRepositoryEventListenerTest {

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private BookRepository repository;

	private Book book;

	private Comment comment;

	@BeforeEach
	void setUp() {
		mongoOperations.dropCollection(Book.class);
		mongoOperations.dropCollection(Comment.class);
		book = mongoOperations.insert(new Book("Title", new Genre("Genre"), singletonList(new Author("Author"))));
		comment = mongoOperations.insert(new Comment("User", "Text", book));
	}

	@Test
	@DisplayName("should delete all comments after book deleting")
	void onAfterDelete() {
		repository.delete(book);
		val result = mongoOperations.findById(comment.getId(), Comment.class);

		assertThat(result).isNull();
	}
}
