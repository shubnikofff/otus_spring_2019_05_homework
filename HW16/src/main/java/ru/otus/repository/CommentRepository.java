package ru.otus.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.domain.Comment;

import java.util.Collection;

@RepositoryRestResource(path = "comment")
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {

	@RestResource(path = "books", rel = "books")
	Collection<Comment> findByBookId(@Param("bookid") Long bookId);
}
