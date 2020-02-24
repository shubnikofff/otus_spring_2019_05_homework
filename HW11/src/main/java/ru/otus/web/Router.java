package ru.otus.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.web.handler.AuthorHandler;
import ru.otus.web.handler.BookHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Router {

	private final static String BASE_URL = "/api";

	@Bean
	public RouterFunction<ServerResponse> bookRouterFunction(BookHandler handler) {
		return route()
				.GET(BASE_URL + "/books", accept(APPLICATION_JSON), handler::getAllBooks)
				.GET(BASE_URL + "/books/{id}", accept(APPLICATION_JSON), handler::getBook)
				.POST(BASE_URL + "/books", accept(APPLICATION_JSON), handler::createBook)
				.PUT(BASE_URL + "/books/{id}", accept(APPLICATION_JSON), handler::updateBook)
				.DELETE(BASE_URL + "/books/{id}", handler::deleteBook)
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> authorRouterFunction(AuthorHandler handler) {
		return route()
				.GET(BASE_URL + "/authors", accept(APPLICATION_JSON), handler::getAllAuthors)
				.PUT(BASE_URL + "/authors/{name}", handler::updateAuthor)
				.build();
	}
}
