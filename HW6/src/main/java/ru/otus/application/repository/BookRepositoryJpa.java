package ru.otus.application.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.model.Book;
import ru.otus.domain.repository.BookRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BookRepositoryJpa implements BookRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	@Override
	public List<Book> findAll() {
		return entityManager.createQuery("select b from Book b join fetch b.genre", Book.class).getResultList();
	}

	@Transactional
	@Override
	public Book save(Book book) {
		if (book.getId() <= 0) {
			entityManager.persist(book);
		} else {
			entityManager.merge(book);
		}

		return book;
	}

	@Transactional
	@Override
	public void remove(Book book) {
		entityManager.remove(entityManager.contains(book) ? book : entityManager.merge(book));
	}
}
