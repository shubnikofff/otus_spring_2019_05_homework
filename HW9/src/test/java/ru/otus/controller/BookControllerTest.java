package ru.otus.controller;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.controller.form.BookForm;
import ru.otus.controller.form.CommentForm;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;
import ru.otus.domain.model.Genre;
import ru.otus.repository.BookRepository;
import ru.otus.repository.BookRepositoryCustomImpl;
import ru.otus.repository.CommentRepository;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

	private final static String ID = "id";

	private final static String NONEXISTENT_ID = "undefined";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookRepository bookRepository;

	@MockBean
	private BookRepositoryCustomImpl bookRepositoryCustom;

	@MockBean
	private CommentRepository commentRepository;

	@Test
	@DisplayName("GET /")
	void getBookList() throws Exception {
		val books = singletonList(new Book("Book", new Genre("Genre"), new Author("Author")));
		when(bookRepository.findAll()).thenReturn(books);

		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("book/list"))
				.andExpect(model().size(1))
				.andExpect(model().attribute("books", books));
	}

	@Test
	@DisplayName("GET /book/{id}/details")
	void getBookDetails() throws Exception {
		val book = new Book("Book", new Genre("Genre"), new Author("Author"));
		book.setId(ID);
		val comments = singletonList(new Comment("User", "Text", book));

		when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
		when(bookRepository.findById(NONEXISTENT_ID)).thenReturn(Optional.empty());
		when(commentRepository.findByBookId(ID)).thenReturn(comments);

		mockMvc.perform(get("/book/" + NONEXISTENT_ID + "/details"))
				.andExpect(status().isNotFound());

		mockMvc.perform(get("/book/{id}/details", ID))
				.andExpect(status().isOk())
				.andExpect(view().name("book/details"))
				.andExpect(model().size(2))
				.andExpect(model().attribute("book", book))
				.andExpect(model().attribute("comments", comments));
	}

	@Test
	@DisplayName("GET /book/create")
	void getNewBookForm() throws Exception {
		mockMvc.perform(get("/book/create"))
				.andExpect(status().isOk())
				.andExpect(view().name("book/form"));
	}

	@Test
	@DisplayName("POST /book/crete")
	void createBook() throws Exception {
		val id = "id";
		val form = new BookForm("Title", "Genre", "Author #1, Author #2");
		val book = new Book("Book", new Genre("Genre"), new Author("Author #1"), new Author("Author #2"));
		book.setId(id);

		when(bookRepository.save(Mapper.map(form))).thenReturn(book);

		mockMvc.perform(post("/book/create")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("title", form.getTitle())
				.param("genre", form.getGenre())
				.param("authors", form.getAuthors()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/book/" + id + "/details"))
				.andExpect(model().attribute("book", book));
	}

	@Test
	@DisplayName("GET /book/{id}/update")
	void getBookForm() throws Exception {
		val book = new Book("Book", new Genre("Genre"), new Author("Author"));
		book.setId(ID);

		when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
		when(bookRepository.findById(NONEXISTENT_ID)).thenReturn(Optional.empty());

		mockMvc.perform(get("/book/{id}/update", NONEXISTENT_ID))
				.andExpect(status().isNotFound());

		mockMvc.perform(get("/book/{id}/update", ID))
				.andExpect(status().isOk())
				.andExpect(view().name("book/form"))
				.andExpect(model().size(2))
				.andExpect(model().attribute("id", ID));
	}

	@Test
	@DisplayName("POST /book/{id}/update")
	void updateBook() throws Exception {
		val form = new BookForm("Title", "Genre", "Author #1, Author #2");
		val book = new Book("Book", new Genre("Genre"), new Author("Author"));
		book.setId(ID);

		when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
		when(bookRepository.findById(NONEXISTENT_ID)).thenReturn(Optional.empty());
		when(bookRepository.save(Mapper.map(form))).thenReturn(book);

		mockMvc.perform(post("/book/{id}/update", NONEXISTENT_ID))
				.andExpect(status().isNotFound());

		mockMvc.perform(post("/book/{id}/update", ID)
				.param("title", form.getTitle())
				.param("genre", form.getGenre())
				.param("authors", form.getAuthors()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/book/" + ID + "/details"));
	}

	@Test
	@DisplayName("POST /book/{id}/add-comment")
	void addComment() throws Exception {
		val book = new Book();
		val form = new CommentForm("User", "Text");
		book.setId(ID);

		when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
		when(bookRepository.findById(NONEXISTENT_ID)).thenReturn(Optional.empty());
		when(commentRepository.save(Mapper.map(form, book))).thenReturn(new Comment("User", "Text", book));

		mockMvc.perform(post("/book/{id}/add-comment", NONEXISTENT_ID))
				.andExpect(status().isNotFound());

		mockMvc.perform(post("/book/{id}/add-comment", ID)
				.param("user", form.getUser())
				.param("text", form.getText()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/book/" + ID + "/details"));
	}

	@Test
	@DisplayName("GET /book/{id}/delete")
	void getDeleteForm() throws Exception {
		val book = new Book("Book", new Genre("Genre"), new Author("Author"));
		book.setId(ID);

		when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
		when(bookRepository.findById(NONEXISTENT_ID)).thenReturn(Optional.empty());

		mockMvc.perform(get("/book/{id}/delete", NONEXISTENT_ID))
				.andExpect(status().isNotFound());

		mockMvc.perform(get("/book/{id}/delete", ID))
				.andExpect(status().isOk())
				.andExpect(view().name("book/delete"))
				.andExpect(model().size(1))
				.andExpect(model().attribute("book", book));
	}

	@Test
	@DisplayName("POST /book/{id}/delete")
	void deleteBook() throws Exception {
		val id = "id";
		mockMvc.perform(post("/book/" + id + "/delete"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));
	}
}
