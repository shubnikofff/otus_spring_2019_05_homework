package ru.otus.web.handler;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.configuration.ApiProperties;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;
import ru.otus.web.request.UpdateAuthorRequest;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureWebTestClient
@DisplayName("Author handler")
class AuthorHandlerTest {

	@Autowired
	private ReactiveMongoOperations mongoOperations;

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private ApiProperties apiProperties;

	@BeforeEach
	void setUp() {
		val bookList = asList(
				new Book("Book #1", new Genre("Genre #1"), asList(new Author("Author #1"), new Author("Author #2"))),
				new Book("Book #2", new Genre("Genre #1"), singletonList(new Author("Author #1"))),
				new Book("Book #3", new Genre("Genre #2"), singletonList(new Author("Author #2")))
		);

		mongoOperations
				.dropCollection(Book.class)
				.thenMany(Flux.fromIterable(bookList))
				.flatMap(mongoOperations::insert)
				.doOnNext(book -> System.out.println("Inserted book: " + book))
				.blockLast();
	}

	@Test
	@DisplayName("GET /authors")
	void getAllAuthors() {
		webTestClient.get().uri(apiProperties.getBaseUrl().concat("/authors"))
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBodyList(Author.class)
				.hasSize(2);
	}

	@Test
	@DisplayName("PUT /authors/{name}")
	void updateAuthor() {
		val request = new UpdateAuthorRequest("New name");

		webTestClient.put().uri(apiProperties.getBaseUrl().concat("/authors/{name}"),"Author #1")
				.contentType(APPLICATION_JSON)
				.body(Mono.just(request), UpdateAuthorRequest.class)
				.exchange()
				.expectStatus().isNoContent()
				.expectBody(Void.class);
	}

	@Test
	@DisplayName("PUT /authors/{name} - NotFound")
	void updateAuthor_NotFound() {
		val request = new UpdateAuthorRequest("New name");

		webTestClient.put().uri(apiProperties.getBaseUrl().concat("/authors/{name}"),"undefined")
				.contentType(APPLICATION_JSON)
				.body(Mono.just(request), UpdateAuthorRequest.class)
				.exchange()
				.expectStatus().isNotFound();
	}
}
