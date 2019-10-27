package ru.otus.application.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.model.Author;
import ru.otus.domain.repository.AuthorRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Author> findAll() {
		return entityManager.createQuery("select a from Author a", Author.class).getResultList();
	}

	@Transactional
	@Override
	public Author save(Author author) {
		if (author.getId() <= 0) {
			entityManager.persist(author);
		} else {
			entityManager.merge(author);
		}

		return author;
	}

	@Transactional
	@Override
	public void remove(Author author) {
		entityManager.remove(author);
	}
}
