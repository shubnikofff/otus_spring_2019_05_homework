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
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.web.controller.BookController;
import ru.otus.web.request.SaveBookRequest;
import ru.otus.web.service.BookService;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;

	@Test
	@DisplayName("GET /book/list")
	@WithMockUser("admin")
	void getAllBooks() throws Exception {
		val books = singletonList(new Book("Book", new Genre("Genre"), new Author("Author")));

		when(bookService.getAllBooks()).thenReturn(books);

		mockMvc.perform(get("/book/list"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("book/list"))
				.andExpect(model().size(1))
				.andExpect(model().attribute("books", books));
	}

	@Test
	@DisplayName("GET /book/{id}/details")
	@WithMockUser("admin")
	void getBook() throws Exception {
		val bookId = "id";
		val book = new Book("Book", new Genre("Genre"), new Author("Author"));
		val comments = singletonList(new Comment("User", "Text", book));

		when(bookService.getBook(bookId)).thenReturn(Optional.of(book));
		when(bookService.getBookComments(book)).thenReturn(comments);

		mockMvc.perform(get("/book/{id}/details", bookId))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("book/details"))
				.andExpect(model().size(2))
				.andExpect(model().attribute("book", book))
				.andExpect(model().attribute("comments", comments));
	}

	@Test
	@DisplayName("GET /book/{id}/details - NotFound")
	@WithMockUser("admin")
	void getBook_NotFound() throws Exception {
		val bookId = "id";

		when(bookService.getBook(bookId)).thenReturn(Optional.empty());

		mockMvc.perform(get("/book/{id}/details", bookId))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(view().name("book/not-found"))
				.andExpect(model().size(0));
	}

	@Test
	@DisplayName("GET /book/create")
	@WithMockUser("admin")
	void getCreateBookView() throws Exception {
		mockMvc.perform(get("/book/create"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("book/form"))
				.andExpect(model().size(0));
	}

	@Test
	@DisplayName("POST /book/create")
	@WithMockUser("admin")
	void createBook() throws Exception {
		val book = new Book("id","Book", new Genre("Genre"), singletonList(new Author("Author")));
		val request = new SaveBookRequest("Book", "Genre", "Author");

		when(bookService.createBook(request)).thenReturn(book);

		mockMvc.perform(post("/book/create")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("title", request.getTitle())
				.param("genre", request.getGenre())
				.param("authors", request.getAuthors()))
				.andDo(print())
				.andExpect(status().isFound())
				.andExpect(view().name(String.format("redirect:/book/%s/details", book.getId())))
				.andExpect(model().attribute("book", book))
				.andExpect(model().size(1));
	}

	@Test
	@DisplayName("GET /book/{id}/update")
	@WithMockUser("admin")
	void getUpdateBookView() throws Exception {
		val bookId = "id";
		val book = new Book(bookId,"Book", new Genre("Genre"), singletonList(new Author("Author")));

		when(bookService.getBook(bookId)).thenReturn(Optional.of(book));

		mockMvc.perform(get("/book/{id}/update", bookId))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("book/form"))
				.andExpect(model().size(4))
				.andExpect(model().attribute("id", bookId))
				.andExpect(model().attribute("bookTitle", book.getTitle()))
				.andExpect(model().attribute("genre", book.getGenre().getName()))
				.andExpect(model().attribute("authors", book.getAuthors().get(0).getName()));
	}

	@Test
	@DisplayName("GET /book/{id}/update - NotFound")
	@WithMockUser("admin")
	void getUpdateBookView_NotFound() throws Exception {
		val bookId = "id";

		when(bookService.getBook(bookId)).thenReturn(Optional.empty());

		mockMvc.perform(get("/book/{id}/update", bookId))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(view().name("book/not-found"))
				.andExpect(model().size(0));
	}

	@Test
	@DisplayName("POST /book/{id}/update")
	@WithMockUser("admin")
	void updateBook() throws Exception {
		val bookId = "id";
		val book = new Book(bookId,"Book", new Genre("Genre"), singletonList(new Author("Author")));
		val request = new SaveBookRequest("Update", "Novel", "Pushkin");
		val result = new Book(bookId,"Update", new Genre("Novel"), singletonList(new Author("Pushkin")));

		when(bookService.getBook(bookId)).thenReturn(Optional.of(book));
		when(bookService.updateBook(book, request)).thenReturn(result);

		mockMvc.perform(post("/book/{id}/update", bookId)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("title", request.getTitle())
				.param("genre", request.getGenre())
				.param("authors", request.getAuthors()))
				.andDo(print())
				.andExpect(status().isFound())
				.andExpect(view().name(String.format("redirect:/book/%s/details", book.getId())))
				.andExpect(model().size(0));
	}

	@Test
	@DisplayName("POST /book/{id}/update - NotFound")
	@WithMockUser("admin")
	void updateBook_NotFound() throws Exception {
		val bookId = "id";
		val request = new SaveBookRequest("Update", "Novel", "Pushkin");

		when(bookService.getBook(bookId)).thenReturn(Optional.empty());

		mockMvc.perform(post("/book/{id}/update", bookId)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("title", request.getTitle())
				.param("genre", request.getGenre())
				.param("authors", request.getAuthors()))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(view().name("book/not-found"))
				.andExpect(model().size(0));
	}

	@Test
	@DisplayName("GET /book/{id}/delete")
	@WithMockUser("admin")
	void getDeleteBookView() throws Exception {
		val bookId = "id";
		val book = new Book(bookId,"Book", new Genre("Genre"), singletonList(new Author("Author")));

		when(bookService.getBook(bookId)).thenReturn(Optional.of(book));

		mockMvc.perform(get("/book/{id}/delete", bookId))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("book/delete"))
				.andExpect(model().size(1))
				.andExpect(model().attribute("book", book));
	}

	@Test
	@DisplayName("GET /book/{id}/delete - NotFound")
	@WithMockUser("admin")
	void getDeleteBookView_NotFound() throws Exception {
		val bookId = "id";

		when(bookService.getBook(bookId)).thenReturn(Optional.empty());

		mockMvc.perform(get("/book/{id}/delete", bookId))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(view().name("book/not-found"))
				.andExpect(model().size(0));
	}

	@Test
	@DisplayName("POST /book/{id}/delete")
	@WithMockUser("admin")
	void deleteBook() throws Exception {
		val bookId = "id";
		val book = new Book(bookId,"Book", new Genre("Genre"), singletonList(new Author("Author")));

		when(bookService.getBook(bookId)).thenReturn(Optional.of(book));

		mockMvc.perform(post("/book/{id}/delete", bookId))
				.andDo(print())
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/"))
				.andExpect(model().size(0));
	}

	@Test
	@DisplayName("POST /book/{id}/delete - NotFound")
	@WithMockUser("admin")
	void deleteBook_NotFound() throws Exception {
		val bookId = "id";

		when(bookService.getBook(bookId)).thenReturn(Optional.empty());

		mockMvc.perform(post("/book/{id}/delete", bookId))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(view().name("book/not-found"))
				.andExpect(model().size(0));
	}
}
