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
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.web.request.UpdateAuthorRequest;
import ru.otus.web.service.AuthorService;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
@DisplayName("Author controller")
class AuthorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthorService authorService;

	@Test
	@DisplayName("GET /author/list")
	@WithMockUser("admin")
	void getAllAuthors() throws Exception {
		val authors = singletonList(new Author("Author name"));
		when(authorService.getAllAuthors()).thenReturn(authors);

		mockMvc.perform(get("/author/list"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("author/list"))
				.andExpect(model().size(1))
				.andExpect(model().attribute("authors", authors));
	}

	@Test
	@DisplayName("GET /author/{name}/details")
	@WithMockUser("admin")
	void getAuthor() throws Exception {
		val name = "name";
		val author = new Author(name);
		val books = singletonList(new Book());

		when(authorService.getAuthor(name)).thenReturn(Optional.of(author));
		when(authorService.getAuthorBooks(author)).thenReturn(books);

		mockMvc.perform(get("/author/{name}/details", name))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("author/details"))
				.andExpect(model().size(2))
				.andExpect(model().attribute("name", name))
				.andExpect(model().attribute("books", books));
	}

	@Test
	@DisplayName("GET /author/{name}/details - NotFound")
	@WithMockUser("admin")
	void getAuthor_NotFound() throws Exception {
		val name = "name";

		when(authorService.getAuthor(name)).thenReturn(Optional.empty());

		mockMvc.perform(get("/author/{name}/details", name))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(view().name("author/not-found"))
				.andExpect(model().size(0));
	}

	@Test
	@DisplayName("POST /author/{name}")
	@WithMockUser("admin")
	void updateAuthor() throws Exception {
		val name = "Lermontov";
		val request = new UpdateAuthorRequest("Pushkin");
		val author = new Author(name);
		val updatedAuthor = new Author("Pushkin");

		when(authorService.getAuthor(name)).thenReturn(Optional.of(author));
		when(authorService.updateAuthor(author, request)).thenReturn(updatedAuthor);

		mockMvc.perform(post("/author/{name}", name)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("name", request.getName()))
				.andDo(print())
				.andExpect(status().isFound())
				.andExpect(view().name(String.format("redirect:/author/%s/details", updatedAuthor.getName())))
				.andExpect(model().size(0));
	}

	@Test
	@DisplayName("POST /author/{name} - NotFound")
	@WithMockUser("admin")
	void updateAuthor_NotFound() throws Exception {
		val name = "name";

		when(authorService.getAuthor(name)).thenReturn(Optional.empty());

		mockMvc.perform(post("/author/{name}", name))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(view().name("author/not-found"))
				.andExpect(model().size(0));
	}
}
