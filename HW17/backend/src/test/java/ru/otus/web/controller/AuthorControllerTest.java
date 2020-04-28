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
import ru.otus.domain.Author;
import ru.otus.web.request.UpdateAuthorRequest;
import ru.otus.web.service.AuthorService;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
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
	@DisplayName("GET /author")
	void getAllAuthors() throws Exception {
		val author = new Author("name");
		val authors = singletonList(author);

		given(service.getAll()).willReturn(authors);

		mockMvc.perform(get("/author"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].name", is(author.getName())));
	}

	@Test
	@DisplayName("PUT /author/{name}")
	void updateAuthor() throws Exception {
		val name = "name";
		val request = new UpdateAuthorRequest("new name");
		val nameCaptor = ArgumentCaptor.forClass(String.class);
		val requestCaptor = ArgumentCaptor.forClass(UpdateAuthorRequest.class);

		mockMvc.perform(put("/author/{name}", name)
				.content(objectAsBytes(request))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		verify(service, times(1)).update(nameCaptor.capture(), requestCaptor.capture());
		assertThat(requestCaptor.getValue().getName()).isEqualTo(request.getName());
		assertThat(nameCaptor.getValue()).isEqualTo(name);
	}
}
