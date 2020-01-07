package ru.otus.domain.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.configuration", "ru.otus.domain.repository", "ru.otus.application.repository.event"})
@DisplayName("BookRepository")
class BookRepositoryTest {
	public static final int INITIAL_BOOK_QUANTITY = 3;

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Test
	@DisplayName("should find all books")
	void findAll() {
		assertThat(bookRepository.findAll()).isNotNull().hasSize(INITIAL_BOOK_QUANTITY);
	}

	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	@Test
	@DisplayName("should save book")
	void save() {
		val book = new Book("New book", new Genre("Genre"), new Author("Author"));
		bookRepository.save(book);
		assertThat(mongoOperations.count(new Query(), Book.class)).isEqualTo(INITIAL_BOOK_QUANTITY + 1);

		val expectedBook = mongoOperations.findOne(Query.query(Criteria.where("_id").is(book.getId())), Book.class);
		assertThat(book).isEqualToComparingFieldByField(expectedBook);
	}

	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	@Test
	@DisplayName("should delete book with comments")
	void delete() {
		val book = bookRepository.findAll().get(0);
		bookRepository.delete(book);

		val comments = commentRepository.findByBookId(book.getId());
		assertThat(comments).isEmpty();
	}
}
