package ru.otus.web.handler;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.configuration.ApiProperties;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;
import ru.otus.repository.BookRepository;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureWebTestClient(timeout = "100000")
class BookHandlerTest {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private BookRepository repository;

	@Autowired
	private ApiProperties apiProperties;

	private List<Book> bookList;

	@BeforeEach
	void setUp() {
		bookList = asList(
				new Book("Book #1", new Genre("Genre #1"), asList(new Author("Author #1"), new Author("Author #2"))),
				new Book("Book #2", new Genre("Genre #1"), singletonList(new Author("Author #1"))),
				new Book("Book #3", new Genre("Genre #2"), singletonList(new Author("Author #2")))
		);

		repository
				.deleteAll()
				.thenMany(Flux.fromIterable(bookList))
				.flatMap(repository::save)
				.doOnNext(book -> System.out.println("Inserted book: " + book))
				.blockLast();
	}

	@Test
	@DisplayName("GET /books")
	void getAllBooks() {
		webTestClient.get().uri(apiProperties.getBaseUrl().concat("/books"))
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBodyList(Book.class)
				.hasSize(3);
	}

	@Test
	@DisplayName("GET /books/{id}")
	void getBook() {
		webTestClient.get().uri(apiProperties.getBaseUrl().concat("/books/{id}"), bookList.get(0).getId())
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBody(Book.class)
				.isEqualTo(bookList.get(0));
	}

	@Test
	@DisplayName("GET /books/{id} - NotFound")
	void getBook_notFound() {
		webTestClient.get().uri(apiProperties.getBaseUrl().concat("/books/{id}"), "undefined")
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	void createBook() {
		val book = new Book("Book", new Genre("Genre"), emptyList());

		webTestClient.post().uri(apiProperties.getBaseUrl().concat("/books"))
				.contentType(APPLICATION_JSON)
				.body(Mono.just(book), Book.class)
				.exchange()
				.expectStatus().isCreated();
	}

	@Test
	void updateBook() {

	}

	@Test
	void deleteBook() {
	}
}
