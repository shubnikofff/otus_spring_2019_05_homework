package ru.otus.web;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootTest
public class BookRouterFunction {

	private final static String BASE_URL = "/api";

	@Autowired
	private RouterFunction<ServerResponse> router;

	@Test
	@DisplayName("GET /books")
	void getAll() {
		val client = WebTestClient.bindToRouterFunction(router).build();

		client.get()
				.uri(BASE_URL  + "/books")
				.exchange()
				.expectStatus()
				.isOk();
	}
}
