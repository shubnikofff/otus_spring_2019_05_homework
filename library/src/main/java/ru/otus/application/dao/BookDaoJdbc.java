package ru.otus.application.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.application.dao.relation.BookAuthorRelation;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;
import ru.otus.domain.service.dao.AuthorDao;
import ru.otus.domain.service.dao.BookDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class BookDaoJdbc implements BookDao {
	private final NamedParameterJdbcOperations jdbcOperations;
	private final AuthorDao authorDao;

	@Override
	public List<Book> getAll() {
		final List<BookAuthorRelation> bookAuthorRelations = getAllBookAuthorRelations();
		final String sql = "select b.id, b.title, g.id as genre_id, g.name as genre_name from BOOKS b left outer join genres g on b.genre_id = g.id;";
		final List<Book> books = jdbcOperations.query(sql, new BookMapper());
		final List<Author> authors = authorDao.getUsed();

		mergeAuthors(books, authors, bookAuthorRelations);
		return books;
	}

	@Override
	public Book getById(Long id) {
		return null;
	}

	@Override
	public int save(Book book) {
		return 0;
	}

	@Override
	public int deleteById(Long id) {
		final Map<String, Long> params = Collections.singletonMap("id", id);
		jdbcOperations.update("delete from books_authors where book_id = :id;", params);
		return jdbcOperations.update("delete from books where id = :id;", params);
	}

	private void mergeAuthors(List<Book> books, List<Author> authors, List<BookAuthorRelation> relations) {
		final Map<Long, Book> bookMap = books.stream().collect(Collectors.toMap(Book::getId, b -> b));
		final Map<Long, Author> authorMap = authors.stream().collect(Collectors.toMap(Author::getId, a -> a));
		relations.forEach(r -> {
			if (bookMap.containsKey(r.getBookId()) && authorMap.containsKey(r.getAuthorId())) {
				bookMap.get(r.getBookId()).getAuthors().add(authorMap.get(r.getAuthorId()));
			}
		});
	}

	private List<BookAuthorRelation> getAllBookAuthorRelations() {
		final String sql = "select book_id, author_id from books_authors order by book_id, author_id;";
		return jdbcOperations.query(sql, new BookAuthorRelationMapper());
	}

	private static class BookAuthorRelationMapper implements RowMapper<BookAuthorRelation> {

		@Override
		public BookAuthorRelation mapRow(ResultSet rs, int rowNum) throws SQLException {
			final long bookId = rs.getLong("book_id");
			final long authorId = rs.getLong("author_id");
			return new BookAuthorRelation(bookId, authorId);
		}
	}

	private static class BookMapper implements RowMapper<Book> {

		@Override
		public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
			final long id = rs.getLong("id");
			final String title = rs.getString("title");
			final long genreId = rs.getLong("genre_id");
			final String genreName = rs.getString("genre_name");

			return new Book(id, title, new ArrayList<>(), new Genre(genreId, genreName));
		}
	}
}
