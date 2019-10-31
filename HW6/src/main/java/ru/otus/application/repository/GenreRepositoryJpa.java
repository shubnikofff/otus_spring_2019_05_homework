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
		final TypedQuery<Genre> query = entityManager.createQuery("select g from Genre g where g.name = :name", Genre.class);
		Genre result;

		try {
			result = query.setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			result = null;
		}

		return Optional.ofNullable(result);
	}

	@Override
	public Genre save(Genre genre) {
		if(genre.getId() <= 0) {
			entityManager.persist(genre);
		} else {
			entityManager.merge(genre);
		}

		return genre;
	}

	@Override
	public void remove(Genre genre) {
		entityManager.remove(entityManager.contains(genre) ? genre : entityManager.merge(genre));
	}
}
