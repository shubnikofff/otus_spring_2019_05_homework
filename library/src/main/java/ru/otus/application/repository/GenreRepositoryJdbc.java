package ru.otus.application.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.model.Genre;
import ru.otus.domain.service.repository.GenreRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GenreRepositoryJdbc implements GenreRepository {
	private final NamedParameterJdbcOperations jdbcOperations;

	public GenreRepositoryJdbc(NamedParameterJdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	@Override
	public List<Genre> getAll() {
		return jdbcOperations.query("select id, name from genres;", new GenreMapper());
	}

	@Override
	public Genre getById(Long id) {
		final Map<String, Object> params = Collections.singletonMap("id", id);
		return jdbcOperations.queryForObject("select id, name from genres where id = :id", params, new GenreMapper());
	}

	@Override
	public int save(Genre genre) {
		final Map<String, Object> params = new HashMap<>(2);
		params.put("id", genre.getId());
		params.put("name", genre.getName());

		final String sql = genre.getId() == null
				? "insert into genres (name) values (:name)"
				: "update genres set name = :name where id = :id";

		return jdbcOperations.update(sql, params);
	}

	@Override
	public int deleteById(Long id) {
		final Map<String, Object> params = Collections.singletonMap("id", id);
		return jdbcOperations.update("delete from genres where id = :id", params);
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
