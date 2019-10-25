package ru.otus.application.dao;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.model.Genre;
import ru.otus.domain.dao.GenreDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@AllArgsConstructor
public class GenreDaoJdbc implements GenreDao {
	private final NamedParameterJdbcOperations jdbcOperations;

	@Override
	public List<Genre> findAll() {
		return jdbcOperations.query("select id, name from genres;", new GenreMapper());
	}

	@Override
	public Genre findByById(Long id) {
		final Map<String, Long> params = Collections.singletonMap("id", id);
		final String sql = "select id, name from genres where id = :id;";
		try {
			return jdbcOperations.queryForObject(sql, params, new GenreMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Genre findByName(String name) {
		final Map<String, String> params = Collections.singletonMap("name", name);
		final String sql = "select id, name from genres where name = :name;";
		try {
			return jdbcOperations.queryForObject(sql, params, new GenreMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long insert(Genre genre) {
		final SqlParameterSource params = new MapSqlParameterSource(Collections.singletonMap("name", genre.getName()));
		final KeyHolder keyHolder = new GeneratedKeyHolder();
		final String sql = "insert into genres (name) values (:name);";
		jdbcOperations.update(sql, params, keyHolder);
		return Objects.requireNonNull(keyHolder.getKey()).longValue();
	}

	@Override
	public int update(Genre genre) {
		final Map<String, Object> params = new HashMap<>(2);
		params.put("id", genre.getId());
		params.put("name", genre.getName());

		return jdbcOperations.update("update genres set name = :name where id = :id;", params);
	}

	@Override
	public int deleteById(Long id) {
		final Map<String, Object> params = Collections.singletonMap("id", id);
		return jdbcOperations.update("delete from genres where id = :id;", params);
	}

	private static class GenreMapper implements RowMapper<Genre> {
		@Override
		public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
			final long id = resultSet.getInt("id");
			final String name = resultSet.getString("name");
			return new Genre(id, name);
		}
	}
}
