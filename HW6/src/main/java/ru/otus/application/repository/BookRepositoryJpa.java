package ru.otus.application.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.model.Book;
import ru.otus.domain.repository.BookRepository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BookRepositoryJpa implements BookRepository {

	@PersistenceContext
	private final EntityManager entityManager;

	@Override
	public List<Book> findAll() {
		return entityManager.createQuery("select b from Book b join fetch b.genre", Book.class).getResultList();
	}

	@Override
	public Optional<Book> findById(Long id) {
		final TypedQuery<Book> query = entityManager
				.createQuery("select b from Book b where b.id = :id", Book.class)
				.setParameter("id", id);

		Book result;
		try {
			result = query.getSingleResult();
		} catch (NoResultException e) {
			result = null;
		}

		return Optional.ofNullable(result);
	}

	@Override
	public Book save(Book book) {
		if (book.getId() == null) {
			entityManager.persist(book);
		} else {
			entityManager.merge(book);
		}

		entityManager.flush();
		return book;
	}

	@Override
	public void deleteById(Long id) {
		entityManager.createQuery("delete from Book b where b.id = :id")
				.setParameter("id", id)
				.executeUpdate();
	}
}
