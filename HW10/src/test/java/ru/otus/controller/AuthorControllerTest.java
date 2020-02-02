package ru.otus.controller;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.domain.model.Author;
import ru.otus.request.UpdateAuthorRequest;
import ru.otus.service.AuthorService;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest extends AbstractControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthorService service;

	@Test
	@DisplayName("GET /authors")
	void getAllAuthors() throws Exception {
		val author = new Author("name");
		val authors = singletonList(author);

		given(service.getAll()).willReturn(authors);

		mockMvc.perform(get("/authors"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].name", is(author.getName())));
	}

	@Test
	@DisplayName("PUT /authors/{name}")
	void update() throws Exception {
		val name = "name";
		val request = new UpdateAuthorRequest("new name");
		val nameCaptor = ArgumentCaptor.forClass(String.class);
		val requestCaptor = ArgumentCaptor.forClass(UpdateAuthorRequest.class);

		mockMvc.perform(put("/authors/{name}", name)
				.content(objectAsBytes(request))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		verify(service, times(1)).update(nameCaptor.capture(), requestCaptor.capture());
		assertThat(requestCaptor.getValue().getName()).isEqualTo(request.getName());
		assertThat(nameCaptor.getValue()).isEqualTo(name);
	}
}
