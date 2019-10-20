package ru.otus.application.dao;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.model.Author;
import ru.otus.domain.service.dao.AuthorDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@AllArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {
	private final NamedParameterJdbcOperations jdbcOperations;

	@Override
	public List<Author> findAll() {
		return jdbcOperations.query("select id, name from authors;", new AuthorMapper());
	}

	@Override
	public List<Author> findAllByNameSet(Set<String> names) {
		final Map<String, Set<String>> params = Collections.singletonMap("names", names);
		final String sql = "select id, name from authors where name in (:names);";
		return jdbcOperations.query(sql, params, new AuthorMapper());
	}

	@Override
	public List<Long> findAllIdsByNameSet(Set<String> names) {
		final Map<String, Set<String>> params = Collections.singletonMap("names", names);
		final String sql = "select id from authors where name in (:names);";
		return jdbcOperations.query(sql, params, (rs, rowNum) -> rs.getLong("id"));
	}

	@Override
	public Author findById(Long id) {
		final Map<String, Long> params = Collections.singletonMap("id", id);
		final String sql = "select id, name from authors where id = :id;";
		try {
			return jdbcOperations.queryForObject(sql, params, new AuthorMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Author findByName(String name) {
		final Map<String, String> params = Collections.singletonMap("name", name);
		final String sql = "select id, name from authors where name = :name;";
		try {
			return jdbcOperations.queryForObject(sql, params, new AuthorMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Author> findByBookId(Long id) {
		final Map<String, Long> params = Collections.singletonMap("id", id);
		final String sql = "select a.id, a.name from authors a inner join books_authors ba on a.ID = ba.author_id where ba.book_id = :id group by a.id, a.name order by a.id;";
		return jdbcOperations.query(sql, params, new AuthorMapper());
	}

	@Override
	public List<Author> findAllUsed() {
		final String sql = "select a.id, a.name from authors a inner join books_authors ba on a.id = ba.author_id group by a.id, a.name order by a.id;";
		return jdbcOperations.query(sql, new AuthorMapper());
	}

	@Override
	public Long insert(Author author) {
		final SqlParameterSource params = new MapSqlParameterSource(Collections.singletonMap("name", author.getName()));
		final KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcOperations.update("insert into authors (name) values (:name);", params, keyHolder);
		return Objects.requireNonNull(keyHolder.getKey()).longValue();
	}

	@Override
	public int insertAll(List<Author> authors) {
		final String sql = "insert into authors (name) values (:name);";
		final SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(authors.toArray());
		return Arrays.stream(jdbcOperations.batchUpdate(sql, batch)).sum();
	}

	@Override
	public int update(Author author) {
		final Map<String, Object> params = new HashMap<>(2);
		params.put("id", author.getId());
		params.put("name", author.getName());

		return jdbcOperations.update("update authors set name = :name where id = :id;", params);
	}

	@Override
	public int deleteById(Long id) {
		final Map<String, Object> params = Collections.singletonMap("id", id);
		return jdbcOperations.update("delete from authors where id = :id;", params);
	}

	private static class AuthorMapper implements RowMapper<Author> {

		@Override
		public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
			final long id = rs.getLong("id");
			final String name = rs.getString("name");
			return new Author(id, name);
		}
	}
}
