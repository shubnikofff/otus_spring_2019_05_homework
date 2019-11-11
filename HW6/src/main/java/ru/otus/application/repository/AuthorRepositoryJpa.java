package ru.otus.application.repository;

import org.springframework.stereotype.Repository;
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

	@Override
	public List<Author> findByNames(List<String> names) {
		return entityManager.createQuery("select a from Author a where a.name in :names", Author.class)
				.setParameter("names", names)
				.getResultList();
	}

	@Override
	public Author save(Author author) {
		if (author.getId() == null) {
			entityManager.persist(author);
		} else {
			entityManager.merge(author);
		}

		entityManager.flush();
		return author;
	}

	@Override
	public void deleteById(Long id) {
		entityManager.createQuery("delete from Author a where a.id = :id")
				.setParameter("id", id)
				.executeUpdate();

		entityManager.flush();
	}
}
