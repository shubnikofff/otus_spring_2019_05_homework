package ru.otus.controller;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;
import ru.otus.repository.BookRepository;
import ru.otus.repository.BookRepositoryCustomImpl;
import ru.otus.repository.CommentRepository;
import ru.otus.repository.GenreRepository;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenreController.class)
class GenreControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GenreRepository genreRepository;

	@MockBean
	private BookRepository bookRepository;

	@MockBean
	private BookRepositoryCustomImpl bookRepositoryCustom;

	@MockBean
	private CommentRepository commentRepository;

	@Test
	@DisplayName("should return model and view with genre list")
	void getGenreList() throws Exception {
		val genres = singletonList(new Genre("Genre name"));
		when(genreRepository.findAll()).thenReturn(genres);
		mockMvc.perform(get("/genre/list"))
				.andExpect(status().isOk())
				.andExpect(view().name("genre/list"))
				.andExpect(model().size(1))
				.andExpect(model().attribute("genres", genres));
	}

	@Test
	@DisplayName("should return model and view with genre details")
	void getGenreDetails() throws Exception {
		val genreName = "Genre name";
		val books = singletonList(new Book("Book", new Genre(genreName), new Author("Author name")));
		when(bookRepository.findByGenreName(genreName)).thenReturn(books);
		mockMvc.perform(get("/genre/" + genreName + "/details"))
				.andExpect(status().isOk())
				.andExpect(view().name("genre/details"))
				.andExpect(model().size(2))
				.andExpect(model().attribute("name", genreName))
				.andExpect(model().attribute("books", books));
	}
}
