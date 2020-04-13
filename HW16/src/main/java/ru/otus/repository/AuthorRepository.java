package ru.otus.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.domain.Author;

@RepositoryRestResource(path = "author")
public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {
}
