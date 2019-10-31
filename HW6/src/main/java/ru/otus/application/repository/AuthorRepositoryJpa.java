package ru.otus.application.repository;

import org.springframework.stereotype.Repository;
import ru.otus.domain.model.Author;
import ru.otus.domain.repository.AuthorRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Author> findAll() {
		return entityManager.createQuery("select a from Author a", Author.class).getResultList();
	}

	@Override
	public List<Author> findByNames(List<String> names) {
		final TypedQuery<Author> query = entityManager.createQuery("select a from Author a where a.name in :names", Author.class);
		query.setParameter("names", names);
		return query.getResultList();
	}

	@Override
	public Author save(Author author) {
		if (author.getId() <= 0) {
			entityManager.persist(author);
		} else {
			entityManager.merge(author);
		}

		return author;
	}

	@Override
	public void remove(Author author) {
		entityManager.remove(entityManager.contains(author) ? author : entityManager.merge(author));
	}
}
