package ru.otus.web.controller;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.web.request.CreateCommentRequest;
import ru.otus.web.request.UpdateCommentRequest;
import ru.otus.web.response.CommentResponse;
import ru.otus.web.service.CommentService;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest extends AbstractControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CommentService service;

	@Test
	@DisplayName("GET /comment")
	void getAllComments() throws Exception {
		val bookId = "bookId";
		val bookIdCaptor = ArgumentCaptor.forClass(String.class);
		val commentResponse = new CommentResponse("id", "User", "Text", bookId);

		given(service.getAll(bookId)).willReturn(singletonList(commentResponse));

		mockMvc.perform(get("/comment")
				.param("bookId", bookId)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(commentResponse.getId())))
				.andExpect(jsonPath("$[0].user", is(commentResponse.getUser())))
				.andExpect(jsonPath("$[0].text", is(commentResponse.getText())))
				.andExpect(jsonPath("$[0].bookId", is(commentResponse.getBookId())));

		verify(service, times(1)).getAll(bookIdCaptor.capture());
		assertThat(bookIdCaptor.getValue()).isEqualTo(bookId);
	}

	@Test
	@DisplayName("POST /comment")
	void createComment() throws Exception {
		val request = new CreateCommentRequest("User", "Text", "bookId");
		val requestCaptor = ArgumentCaptor.forClass(CreateCommentRequest.class);

		mockMvc.perform(post("/comment")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectAsBytes(request)))
				.andExpect(status().isCreated());

		verify(service, times(1)).create(requestCaptor.capture());
		assertThat(requestCaptor.getValue().getUser()).isEqualTo(request.getUser());
		assertThat(requestCaptor.getValue().getText()).isEqualTo(request.getText());
		assertThat(requestCaptor.getValue().getBookId()).isEqualTo(request.getBookId());
	}

	@Test
	@DisplayName("PUT /comment/{id}")
	void updateComment() throws Exception {
		val id = "id";
		val request = new UpdateCommentRequest("User", "Text");
		val idCaptor = ArgumentCaptor.forClass(String.class);
		val requestCaptor = ArgumentCaptor.forClass(UpdateCommentRequest.class);

		mockMvc.perform(put("/comment/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectAsBytes(request)))
				.andExpect(status().isNoContent());

		verify(service, times(1)).update(idCaptor.capture(), requestCaptor.capture());
		assertThat(idCaptor.getValue()).isEqualTo(id);
		assertThat(requestCaptor.getValue().getUser()).isEqualTo(request.getUser());
		assertThat(requestCaptor.getValue().getText()).isEqualTo(request.getText());
	}

	@Test
	@DisplayName("DELETE /comment/{id}")
	void deleteComment() throws Exception {
		val id = "id";
		val idCaptor = ArgumentCaptor.forClass(String.class);

		mockMvc.perform(delete("/comment/{id}", id)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		verify(service, times(1)).delete(idCaptor.capture());
		assertThat(idCaptor.getValue()).isEqualTo(id);
	}
}
