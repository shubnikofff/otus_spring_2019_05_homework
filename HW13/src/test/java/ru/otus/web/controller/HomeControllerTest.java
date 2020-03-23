package ru.otus.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
@DisplayName("Home controller")
class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("GET / - with auth")
	@WithMockUser
	void index_with_auth() throws Exception {
		mockMvc.perform(get("/"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("home"))
				.andExpect(model().size(0));
	}

	@Test
	@DisplayName("GET / - without auth")
	void index_without_auth() throws Exception {
		mockMvc.perform(get("/"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("home"))
				.andExpect(model().size(0));
	}

	@ParameterizedTest
	@ValueSource(strings = {
			"/author/list", "/author/name/details",
			"/book/list", "/book/1/details", "/book/create", "/book/1/update", "/book/1/delete",
			"/comment/1/update",
			"/genre/list", "/genre/name/details",
	})
	@DisplayName("GET Redirect to Login page")
	void denyUnauthorizedGetRequest(String url) throws Exception {
		mockMvc.perform(get(url))
				.andDo(print())
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("http://localhost/login"));
	}

	@ParameterizedTest
	@ValueSource(strings = {
			"/author/name/update",
			"/book/create", "/book/1/update", "/book/1/delete",
			"/comment/create", "/comment/1/update",
			"/author/name/update",
	})
	@DisplayName("POST Redirect to Login page")
	void redirectPostRequestsToLoginPage(String url) throws Exception {
		mockMvc.perform(post(url))
				.andDo(print())
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("http://localhost/login"));
	}
}
