package ru.otus.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByBookId(Long bookId);

	@Override
	<S extends Comment> S save(S entity);

	void deleteByBookId(Long bookId);
}
