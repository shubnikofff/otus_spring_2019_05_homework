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
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.BookRepositoryCustomImpl;
import ru.otus.repository.CommentRepository;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthorRepository authorRepository;

	@MockBean
	private BookRepository bookRepository;

	@MockBean
	private BookRepositoryCustomImpl bookRepositoryCustom;

	@MockBean
	private CommentRepository commentRepository;

	@Test
	@DisplayName("GET /author/list")
	void findAllAuthors() throws Exception {
		val authors = singletonList(new Author("Author name"));
		when(authorRepository.findAll()).thenReturn(authors);
		mockMvc.perform(get("/author/list"))
				.andExpect(status().isOk())
				.andExpect(view().name("author/list"))
				.andExpect(model().size(1))
				.andExpect(model().attribute("authors", authors));
	}

	@Test
	@DisplayName("GET /author/{name}/details")
	void getAuthorDetails() throws Exception {
		val authorName = "Author name";
		val books = singletonList(new Book("Book", new Genre("Genre"), new Author(authorName)));
		when(bookRepository.findByAuthorName(authorName)).thenReturn(books);
		mockMvc.perform(get("/author/" + authorName + "/details"))
				.andExpect(status().isOk())
				.andExpect(view().name("author/details"))
				.andExpect(model().size(2))
				.andExpect(model().attribute("name", authorName))
				.andExpect(model().attribute("books", books));
	}
}
