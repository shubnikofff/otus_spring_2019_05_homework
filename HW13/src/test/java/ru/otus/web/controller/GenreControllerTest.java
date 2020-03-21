package ru.otus.web.controller;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.web.request.UpdateGenreRequest;
import ru.otus.web.service.GenreService;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenreController.class)
@DisplayName("Genre controller")
class GenreControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GenreService genreService;

	@Test
	@DisplayName("GET /genre/list")
	@WithMockUser
	void getAllGenres() throws Exception {
		val genres = singletonList(new Genre("genre"));
		when(genreService.getAllGenres()).thenReturn(genres);

		mockMvc.perform(get("/genre/list"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("genre/list"))
				.andExpect(model().size(1))
				.andExpect(model().attribute("genres", genres));
	}

	@Test
	@DisplayName("GET /genre/{name}/details")
	@WithMockUser
	void getGenre() throws Exception {
		val name = "name";
		val genre = new Genre(name);
		val books = singletonList(new Book());

		when(genreService.getGenre(name)).thenReturn(Optional.of(genre));
		when(genreService.getBooksOfGenre(genre)).thenReturn(books);

		mockMvc.perform(get("/genre/{name}/details", name))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("genre/details"))
				.andExpect(model().size(2))
				.andExpect(model().attribute("name", name))
				.andExpect(model().attribute("books", books));
	}

	@Test
	@DisplayName("GET /genre/{name}/details - NotFound")
	@WithMockUser
	void getGenre_NotFound() throws Exception {
		val name = "name";

		when(genreService.getGenre(name)).thenReturn(Optional.empty());

		mockMvc.perform(get("/genre/{name}/details", name))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(view().name("genre/not-found"))
				.andExpect(model().size(0));
	}

	@Test
	@DisplayName("POST /genre/{name}/update")
	@WithMockUser(roles = "ADMIN")
	void updateGenre() throws Exception {
		val name = "name";
		val request = new UpdateGenreRequest("new name");
		val genre = new Genre(name);
		val updatedGenre = new Genre("new name");

		when(genreService.getGenre(name)).thenReturn(Optional.of(genre));
		when(genreService.updateGenre(genre, request)).thenReturn(updatedGenre);

		mockMvc.perform(post("/genre/{name}/update", name)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("name", request.getName()))
				.andDo(print())
				.andExpect(status().isFound())
				.andExpect(view().name(String.format("redirect:/genre/%s/details", updatedGenre.getName())))
				.andExpect(model().size(0));
	}

	@Test
	@DisplayName("POST /genre/{name}/update - NotFound")
	@WithMockUser(roles = "ADMIN")
	void updateGenre_NotFound() throws Exception {
		val name = "name";

		when(genreService.getGenre(name)).thenReturn(Optional.empty());

		mockMvc.perform(post("/genre/{name}/update", name))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(view().name("genre/not-found"))
				.andExpect(model().size(0));
	}

	@Test
	@WithMockUser(roles = {})
	@DisplayName("POST /genre/{name}/update without role ADMIN")
	void checkPostAuthorWithoutRoleAdmin() throws Exception {
		mockMvc.perform(post("/genre/name/update"))
				.andDo(print())
				.andExpect(status().isForbidden());
	}
}
