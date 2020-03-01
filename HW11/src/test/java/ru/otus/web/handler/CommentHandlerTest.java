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
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;
import ru.otus.domain.model.Genre;
import ru.otus.web.request.CreateCommentRequest;
import ru.otus.web.request.UpdateCommentRequest;
import ru.otus.web.response.CreateCommentResponse;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureWebTestClient
@DisplayName("Author handler")
class CommentHandlerTest {

	private final static String FIRST_BOOK_ID = "1";
	private final static String SECOND_BOOK_ID = "2";

	@Autowired
	private ReactiveMongoOperations mongoOperations;

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private ApiProperties apiProperties;

	private List<Comment> commentList;

	@BeforeEach
	void setUp() {
		commentList = asList(
			new Comment("User #1", "Text", FIRST_BOOK_ID),
			new Comment("User #2", "Text", FIRST_BOOK_ID)	,
			new Comment("User #1", "Text", SECOND_BOOK_ID)
		);

		mongoOperations
				.dropCollection(Comment.class)
				.thenMany(Flux.fromIterable(commentList))
				.flatMap(mongoOperations::insert)
				.doOnNext(comment -> System.out.println("Inserted comment: " + comment))
				.blockLast();
	}

	@Test
	@DisplayName("GET /comments/?bookId")
	void getAllComments() {
		webTestClient.get().uri(apiProperties.getBaseUrl().concat("/comments?bookId={id}"), FIRST_BOOK_ID)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBodyList(Comment.class)
				.hasSize(2);
	}

	@Test
	@DisplayName("POST /comments")
	void createComment() {
		val request = mongoOperations.insert(new Book("Title", new Genre("Genre"), emptyList()))
				.map(book -> new CreateCommentRequest("User", "Text", book.getId()));

		webTestClient.post().uri(apiProperties.getBaseUrl().concat("/comments"))
				.contentType(APPLICATION_JSON)
				.body(request, CreateCommentRequest.class)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(CreateCommentResponse.class);
	}

	@Test
	@DisplayName("POST /comments - NotFound")
	void createComment_NotFound() {
		val request = new CreateCommentRequest("User", "Text", "undefined");

		webTestClient.post().uri(apiProperties.getBaseUrl().concat("/comments"))
				.contentType(APPLICATION_JSON)
				.body(Mono.just(request), CreateCommentRequest.class)
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	@DisplayName("PUT /comments/{id}")
	void updateComment() {
		val request = new UpdateCommentRequest("New text");

		webTestClient.put().uri(apiProperties.getBaseUrl().concat("/comments/{id}"), commentList.get(0).getId())
				.contentType(APPLICATION_JSON)
				.body(Mono.just(request), UpdateCommentRequest.class)
				.exchange()
				.expectStatus().isNoContent()
				.expectBody(Void.class);
	}

	@Test
	@DisplayName("PUT /comments/{id} - NotFound")
	void updateComment_NotFound() {
		val request = new UpdateCommentRequest("New text");

		webTestClient.put().uri(apiProperties.getBaseUrl().concat("/comments/{id}"), "undefined")
				.contentType(APPLICATION_JSON)
				.body(Mono.just(request), UpdateCommentRequest.class)
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	@DisplayName("DELETE /comments/{id}")
	void deleteComment() {
		webTestClient.delete().uri(apiProperties.getBaseUrl().concat("/comments/{id}"), commentList.get(0).getId())
				.accept(APPLICATION_JSON)
				.exchange()
				.expectStatus().isNoContent()
				.expectBody(Void.class);
	}

	@Test
	@DisplayName("DELETE /comments/{id} - NotFound")
	void deleteComment_NotFound() {
		webTestClient.delete().uri(apiProperties.getBaseUrl().concat("/comments/{id}"), "undefined")
				.accept(APPLICATION_JSON)
				.exchange()
				.expectStatus().isNotFound();
	}
}
