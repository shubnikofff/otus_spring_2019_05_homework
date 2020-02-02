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
import ru.otus.domain.model.Genre;
import ru.otus.request.UpdateGenreRequest;
import ru.otus.service.GenreService;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
class GenreControllerTest extends AbstractControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GenreService service;

	@Test
	@DisplayName("GET /genres")
	void getAllGenres() throws Exception {
		val genre = new Genre("genre");
		val genres = singletonList(genre);

		given(service.getAll()).willReturn(genres);

		mockMvc.perform(get("/genres"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].name", is(genre.getName())));
	}

	@Test
	@DisplayName("PUT /genres/{name}")
	void update() throws Exception {
		val name = "Name";
		val request = new UpdateGenreRequest("New name");
		val nameCaptor = ArgumentCaptor.forClass(String.class);
		val requestCaptor = ArgumentCaptor.forClass(UpdateGenreRequest.class);

		mockMvc.perform(put("/genres/{name}", name)
				.content(objectAsBytes(request))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		verify(service, times(1)).update(nameCaptor.capture(), requestCaptor.capture());
		assertThat(requestCaptor.getValue().getName()).isEqualTo(request.getName());
		assertThat(nameCaptor.getValue()).isEqualTo(name);
	}
}
