package ru.otus.application.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.model.Author;
import ru.otus.domain.service.dao.AuthorDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {
	private final NamedParameterJdbcOperations jdbcOperations;

	@Override
	public List<Author> findAll() {
		return jdbcOperations.query("select id, name from authors;", new AuthorMapper());
	}

	@Override
	public Author findById(Long id) {
		final Map<String, Long> params = Collections.singletonMap("id", id);
		return jdbcOperations.queryForObject("select id, name from authors where id = :id;", params, new AuthorMapper());
	}

	@Override
	public Author findByName(String name) {
		final Map<String, String> params = Collections.singletonMap("name", name);
		return jdbcOperations.queryForObject("select id, name from authors where name = :name;", params, new AuthorMapper());
	}

	@Override
	public List<Author> getUsed() {
		return jdbcOperations.query(
				"select a.id, a.name from authors a inner join books_authors ba on a.id = ba.author_id group by a.id, a.name order by a.id;",
				new AuthorMapper()
		);
	}

	@Override
	public int save(Author author) {
		final Map<String, Object> params = new HashMap<>(2);
		params.put("id", author.getId());
		params.put("name", author.getName());

		final String sql = author.getId() == null
				? "insert into authors (name) values (:name);"
				: "update authors set name = :name where id = :id;";

		return jdbcOperations.update(sql, params);
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
