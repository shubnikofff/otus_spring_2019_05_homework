package ru.otus.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.configuration.ApiProperties;
import ru.otus.web.handler.AuthorHandler;
import ru.otus.web.handler.BookHandler;
import ru.otus.web.handler.CommentHandler;
import ru.otus.web.handler.GenreHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Router {

	@Bean
	public RouterFunction<ServerResponse> routerFunction(
			ApiProperties apiProperties,
			AuthorHandler authorHandler,
			BookHandler bookHandler,
			CommentHandler commentHandler,
			GenreHandler genreHandler
	) {
		return route()
				.add(authorsRouterFunction(authorHandler, apiProperties.getBaseUrl()))
				.add(booksRouterFunction(bookHandler, apiProperties.getBaseUrl()))
				.add(commentsRouterFunction(commentHandler, apiProperties.getBaseUrl()))
				.add(genresRouterFunction(genreHandler, apiProperties.getBaseUrl()))
				.build();
	}

	private static RouterFunction<ServerResponse> authorsRouterFunction(AuthorHandler handler, String baseUrl) {
		return route()
				.GET(baseUrl + "/authors", accept(APPLICATION_JSON), handler::getAllAuthors)
				.PUT(baseUrl + "/authors/{name}", handler::updateAuthor)
				.build();
	}

	private static RouterFunction<ServerResponse> booksRouterFunction(BookHandler handler, String baseUrl) {
		return route()
				.GET(baseUrl + "/books", accept(APPLICATION_JSON), handler::getAllBooks)
				.GET(baseUrl + "/books/{id}", accept(APPLICATION_JSON), handler::getBook)
				.POST(baseUrl + "/books", accept(APPLICATION_JSON), handler::createBook)
				.PUT(baseUrl + "/books/{id}", accept(APPLICATION_JSON), handler::updateBook)
				.DELETE(baseUrl + "/books/{id}", handler::deleteBook)
				.build();
	}

	private static RouterFunction<ServerResponse> commentsRouterFunction(CommentHandler handler, String baseUrl) {
		return route()
				.GET(baseUrl + "/comments", accept(APPLICATION_JSON), handler::getAllComments)
				.POST(baseUrl + "/comments", accept(APPLICATION_JSON), handler::createComment)
				.PUT(baseUrl + "/comments/{id}", accept(APPLICATION_JSON), handler::updateComment)
				.DELETE(baseUrl + "/comments/{id}", handler::deleteComment)
				.build();
	}

	private static RouterFunction<ServerResponse> genresRouterFunction(GenreHandler handler, String baseUrl) {
		return route()
				.GET(baseUrl + "/genres", accept(APPLICATION_JSON), handler::getAllGenres)
				.PUT(baseUrl + "/genres/{name}", accept(APPLICATION_JSON), handler::updateGenre)
				.build();
	}
}
