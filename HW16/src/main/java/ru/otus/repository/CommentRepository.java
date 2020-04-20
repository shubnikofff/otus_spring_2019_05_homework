package ru.otus.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.domain.Comment;

@RepositoryRestResource(path = "comment")
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
}
