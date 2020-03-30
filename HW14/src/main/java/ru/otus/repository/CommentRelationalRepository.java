package ru.otus.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.otus.model.CommentRelationalModel;

public interface CommentRelationalRepository extends PagingAndSortingRepository<CommentRelationalModel, Long> {
}
