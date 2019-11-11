package ru.otus.application.repository;

import org.springframework.stereotype.Repository;
import ru.otus.domain.model.Genre;
import ru.otus.domain.repository.GenreRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryJpa implements GenreRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Genre> findAll() {
		return entityManager.createQuery("select g from Genre g", Genre.class).getResultList();
	}

	@Override
	public Optional<Genre> findByName(String name) {
		final TypedQuery<Genre> query = entityManager
				.createQuery("select g from Genre g where g.name = :name", Genre.class)
				.setParameter("name", name);

		Genre result;
		try {
			result = query.getSingleResult();
		} catch (NoResultException e) {
			result = null;
		}

		return Optional.ofNullable(result);
	}

	@Override
	public Genre save(Genre genre) {
		if(genre.getId() == null) {
			entityManager.persist(genre);
		} else {
			entityManager.merge(genre);
		}

		entityManager.flush();
		return genre;
	}

	@Override
	public void deleteById(Long id) {
		entityManager.createQuery("delete from Genre g where g.id = :id")
				.setParameter("id", id)
				.executeUpdate();

		entityManager.flush();
	}
}
