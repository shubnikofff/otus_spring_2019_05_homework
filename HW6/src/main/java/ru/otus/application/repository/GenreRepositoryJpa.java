package ru.otus.application.repository;

import org.springframework.stereotype.Repository;
import ru.otus.domain.model.Genre;
import ru.otus.domain.repository.GenreRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class GenreRepositoryJpa implements GenreRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Genre> findAll() {
		return entityManager.createQuery("select g from Genre g", Genre.class).getResultList();
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
		entityManager.remove(genre);
	}
}
