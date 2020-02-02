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
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;
import ru.otus.request.SaveBookRequest;
import ru.otus.service.BookService;

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

@WebMvcTest(BookController.class)
class BookControllerTest extends AbstractControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService service;

	@Test
	@DisplayName("GET /books")
	void getBooks() throws Exception {
		val book = new Book("id", "Title", new Genre("Genre"), singletonList(new Author("Author")));
		val books = singletonList(book);

		given(service.getAll()).willReturn(books);

		mockMvc.perform(get("/books"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(book.getId())))
				.andExpect(jsonPath("$[0].title", is(book.getTitle())))
				.andExpect(jsonPath("$[0].genre.name", is(book.getGenre().getName())))
				.andExpect(jsonPath("$[0].authors", hasSize(1)))
				.andExpect(jsonPath("$[0].authors[0].name", is(book.getAuthors().get(0).getName())));
	}

	@Test
	@DisplayName("GET /books/{id}")
	void getBook() throws Exception {
		val id = "id";
		val book = new Book(id, "Title", new Genre("Genre"), singletonList(new Author("Author")));

		given(service.getOne(id)).willReturn(book);

		mockMvc.perform(get("/books/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(id)))
				.andExpect(jsonPath("$.title", is(book.getTitle())))
				.andExpect(jsonPath("$.genre.name", is(book.getGenre().getName())))
				.andExpect(jsonPath("$.authors", hasSize(1)))
				.andExpect(jsonPath("$.authors[0].name", is(book.getAuthors().get(0).getName())));
	}

	@Test
	@DisplayName("POST /books")
	void create() throws Exception {
		val request = new SaveBookRequest("Title", "Genre", singletonList("Author"));
		val requestCaptor = ArgumentCaptor.forClass(SaveBookRequest.class);

		mockMvc.perform(post("/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectAsBytes(request)))
				.andExpect(status().isCreated());

		verify(service, times(1)).create(requestCaptor.capture());
		assertThat(requestCaptor.getValue().getTitle()).isEqualTo(request.getTitle());
		assertThat(requestCaptor.getValue().getGenre()).isEqualTo(request.getGenre());
		assertThat(requestCaptor.getValue().getAuthors()).isEqualTo(request.getAuthors());
	}

	@Test
	@DisplayName("PUT /books/{id}")
	void update() throws Exception {
		val id = "id";
		val request = new SaveBookRequest("Title", "Genre", singletonList("Author"));
		val idCaptor = ArgumentCaptor.forClass(String.class);
		val requestCaptor = ArgumentCaptor.forClass(SaveBookRequest.class);

		mockMvc.perform(put("/books/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectAsBytes(request)))
				.andExpect(status().isNoContent());

		verify(service, times(1)).update(idCaptor.capture(), requestCaptor.capture());
		assertThat(idCaptor.getValue()).isEqualTo(id);
		assertThat(requestCaptor.getValue().getTitle()).isEqualTo(request.getTitle());
		assertThat(requestCaptor.getValue().getGenre()).isEqualTo(request.getGenre());
		assertThat(requestCaptor.getValue().getAuthors()).isEqualTo(request.getAuthors());
	}

	@Test
	@DisplayName("DELETE /books/{id}")
	void testDelete() throws Exception {
		val id = "id";
		val idCaptor = ArgumentCaptor.forClass(String.class);

		mockMvc.perform(delete("/books/{id}", id)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		verify(service, times(1)).delete(idCaptor.capture());
		assertThat(idCaptor.getValue()).isEqualTo(id);
	}
}
