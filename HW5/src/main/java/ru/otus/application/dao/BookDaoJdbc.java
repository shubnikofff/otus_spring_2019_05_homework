package ru.otus.application.dao;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.application.dao.relation.BookAuthorRelation;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;
import ru.otus.domain.dao.AuthorDao;
import ru.otus.domain.dao.BookDao;
import ru.otus.domain.dao.GenreDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class BookDaoJdbc implements BookDao {
	private final NamedParameterJdbcOperations jdbcOperations;
	private final AuthorDao authorDao;
	private final GenreDao genreDao;

	@Override
	public List<Book> findAll() {
		final List<BookAuthorRelation> bookAuthorRelations = getAllBookAuthorRelations();
		final String sql = "select b.id, b.title, g.id as genre_id, g.name as genre_name from BOOKS b left outer join genres g on b.genre_id = g.id;";
		final List<Book> books = jdbcOperations.query(sql, new BookMapper());
		final List<Author> authors = authorDao.findAllUsed();

		mergeAuthors(books, authors, bookAuthorRelations);
		return books;
	}

	@Override
	public Book findById(Long id) {
		final Map<String, Long> params = Collections.singletonMap("id", id);
		final String sql = "select b.id, b.title, g.id as genre_id, g.name as genre_name from BOOKS b left outer join genres g on b.genre_id = g.id where b.id = :id;";

		Book book;
		try {
			book = jdbcOperations.queryForObject(sql, params, new BookMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		authorDao.findByBookId(id).forEach(author -> Objects.requireNonNull(book).getAuthors().add(author));
		return book;
	}

	@Override
	public Long insert(Book book) {
		final String sql = "insert into books (title, genre_id) values (:title, :genreId);";
		final Map<String, Object> paramsMap = new HashMap<>(2);
		paramsMap.put("title", book.getTitle());
		paramsMap.put("genreId", getGenreId(book.getGenre()));
		final KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcOperations.update(sql, new MapSqlParameterSource(paramsMap), keyHolder);

		final Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
		updateAuthors(book.getAuthors());
		updateBookAuthorRelations(id, book.getAuthors());
		return id;
	}

	@Override
	public int update(Book book) {
		final String sql = "update books set title = :title, genre_id = :genreId where id = :id;";
		final Map<String, Object> paramsMap = new HashMap<>(3);
		paramsMap.put("id", book.getId());
		paramsMap.put("title", book.getTitle());
		paramsMap.put("genreId", getGenreId(book.getGenre()));
		final int numberOfRowsUpdated = jdbcOperations.update(sql, paramsMap);

		updateAuthors(book.getAuthors());
		updateBookAuthorRelations(book.getId(), book.getAuthors());
		return numberOfRowsUpdated;
	}

	@Override
	public int deleteById(Long id) {
		final Map<String, Long> params = Collections.singletonMap("id", id);
		jdbcOperations.update("delete from books_authors where book_id = :id;", params);
		return jdbcOperations.update("delete from books where id = :id;", params);
	}

	private Long getGenreId(Genre genre) {
		if (genre.getId() != null) {
			return genre.getId();
		}

		Genre foundGenre = genreDao.findByName(genre.getName());
		if (foundGenre != null) {
			return foundGenre.getId();
		}

		return genreDao.insert(genre);
	}

	private void updateAuthors(List<Author> authors) {
		final Set<String> authorNameSet = authors.stream().map(Author::getName).collect(Collectors.toSet());
		final List<Author> existingAuthors = authorDao.findAllByNameSet(authorNameSet);
		final Set<String> existingAuthorNameSet = existingAuthors.stream().map(Author::getName).collect(Collectors.toSet());
		final List<Author> authorsForInsert = authors.stream().filter(author -> !existingAuthorNameSet.contains(author.getName())).collect(Collectors.toList());
		authorDao.insertAll(authorsForInsert);
	}

	private void updateBookAuthorRelations(Long bookId, List<Author> authors) {
		jdbcOperations.update("delete from books_authors where book_id = :bookId;", Collections.singletonMap("bookId", bookId));
		final List<Long> authorIds = authorDao.findAllIdsByNameSet(authors.stream().map(Author::getName).collect(Collectors.toSet()));
		final Object[] relations = authorIds.stream().map(authorId -> new BookAuthorRelation(bookId, authorId)).toArray();
		jdbcOperations.batchUpdate("insert into books_authors (book_id, author_id) values (:bookId, :authorId)", SqlParameterSourceUtils.createBatch(relations));
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
