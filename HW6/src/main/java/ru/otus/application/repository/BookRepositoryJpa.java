package ru.otus.application.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.model.Book;
import ru.otus.domain.repository.BookRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
	public void remove(Book book) {
		entityManager.remove(entityManager.contains(book) ? book : entityManager.merge(book));
		entityManager.flush();
	}
}
