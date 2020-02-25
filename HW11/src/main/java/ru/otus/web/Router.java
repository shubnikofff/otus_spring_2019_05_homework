package ru.otus.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.web.handler.AuthorHandler;
import ru.otus.web.handler.BookHandler;
import ru.otus.web.handler.CommentHandler;
import ru.otus.web.handler.GenreHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Router {

	private final static String BASE_URL = "/api";

	@Bean
	public RouterFunction<ServerResponse> routerFunction(
			AuthorHandler authorHandler,
			BookHandler bookHandler,
			CommentHandler commentHandler,
			GenreHandler genreHandler
	) {
		return route()
				.add(authorsRouterFunction(authorHandler))
				.add(booksRouterFunction(bookHandler))
				.add(commentsRouterFunction(commentHandler))
				.add(genresRouterFunction(genreHandler))
				.build();
	}

	private static RouterFunction<ServerResponse> authorsRouterFunction(AuthorHandler handler) {
		return route()
				.GET(BASE_URL + "/authors", accept(APPLICATION_JSON), handler::getAllAuthors)
				.PUT(BASE_URL + "/authors/{name}", handler::updateAuthor)
				.build();
	}

	private static RouterFunction<ServerResponse> booksRouterFunction(BookHandler handler) {
		return route()
				.GET(BASE_URL + "/books", accept(APPLICATION_JSON), handler::getAllBooks)
				.GET(BASE_URL + "/books/{id}", accept(APPLICATION_JSON), handler::getBook)
				.POST(BASE_URL + "/books", accept(APPLICATION_JSON), handler::createBook)
				.PUT(BASE_URL + "/books/{id}", accept(APPLICATION_JSON), handler::updateBook)
				.DELETE(BASE_URL + "/books/{id}", handler::deleteBook)
				.build();
	}

	private static RouterFunction<ServerResponse> commentsRouterFunction(CommentHandler handler) {
		return route()
				.GET(BASE_URL + "/comments", accept(APPLICATION_JSON), handler::getAllComments)
				.POST(BASE_URL + "/comments", accept(APPLICATION_JSON), handler::createComment)
				.PUT(BASE_URL + "/comments/{id}", accept(APPLICATION_JSON), handler::updateComment)
				.DELETE(BASE_URL + "/comments/{id}", handler::deleteComment)
				.build();
	}

	private static RouterFunction<ServerResponse> genresRouterFunction(GenreHandler handler) {
		return route()
				.GET(BASE_URL + "/genres", accept(APPLICATION_JSON), handler::getAllGenres)
				.PUT(BASE_URL + "/genres/{name}", accept(APPLICATION_JSON), handler::updateGenre)
				.build();
	}
}
