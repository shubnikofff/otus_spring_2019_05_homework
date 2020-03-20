package ru.otus.web.controller;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.web.service.CommentService;

import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
@DisplayName("Comment controller")
class CommentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CommentService commentService;

	@Test
	@DisplayName("POST /comment/create")
	@WithMockUser
	void createComment() throws Exception {
		val bookId = "id";

		when(commentService.getBook(bookId)).thenReturn(Optional.of(new Book(bookId, "Title", new Genre("Genre"), emptyList())));

		mockMvc.perform(post("/comment/create")
				.queryParam("bookId", bookId)
				.param("user", "User")
				.param("text", "Text")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andDo(print())
				.andExpect(status().isFound())
				.andExpect(view().name(String.format("redirect:/book/%s/details", bookId)))
				.andExpect(model().size(0));
	}

	@Test
	@DisplayName("POST /comment/create - NotFound")
	@WithMockUser
	void createComment_NotFound() throws Exception {
		val bookId = "id";

		when(commentService.getBook(bookId)).thenReturn(Optional.empty());

		mockMvc.perform(post("/comment/create")
				.queryParam("bookId", bookId)
				.param("user", "User")
				.param("text", "Text")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(view().name("book/not-found"))
				.andExpect(model().size(0));
	}

	@ParameterizedTest
	@ValueSource(strings = {"/comment/create", "/comment/1/update"})
	@DisplayName("POST Deny unauthorized")
	void denyUnauthorizedPostRequests(String url) throws Exception {
		mockMvc.perform(post(url))
				.andDo(print())
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("http://localhost/login"));
	}

	@Test
	@DisplayName("GET Deny unauthorized")
	void denyUnauthorizedGetRequest() throws Exception {
		mockMvc.perform(get("/comment/1/update"))
				.andDo(print())
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("http://localhost/login"));
	}

	@ParameterizedTest
	@ValueSource(strings = {"/comment/create?bookId=1", "/comment/1/update"})
	@WithMockUser(roles = {"GUEST"})
	@DisplayName("POST Deny without role USER")
	void denyPostRequestsWithoutRoleUser(String url) throws Exception {
		mockMvc.perform(post(url))
				.andDo(print())
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(roles = {})
	@DisplayName("GET Deny without role USER")
	void denyGetRequestWithoutRoleUser() throws Exception {
		mockMvc.perform(get("/comment/1/update"))
				.andDo(print())
				.andExpect(status().isForbidden());
	}
}
