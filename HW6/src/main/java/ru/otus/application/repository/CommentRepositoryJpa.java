package ru.otus.application.repository;

import org.springframework.stereotype.Repository;
import ru.otus.domain.model.Comment;
import ru.otus.domain.repository.CommentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CommentRepositoryJpa implements CommentRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Comment> findAll(Long bookId) {
		return entityManager.createQuery("select c from Comment c where c.book.id = :bookId", Comment.class)
				.setParameter("bookId", bookId)
				.getResultList();
	}

	@Override
	public Comment save(Comment comment) {
		if(comment.getId() == null) {
			entityManager.persist(comment);
		} else {
			entityManager.merge(comment);
		}
		entityManager.flush();

		return comment;
	}
}
