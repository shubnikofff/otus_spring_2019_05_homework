package ru.otus.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.domain.Genre;

@RepositoryRestResource(path = "genre")
public interface GenreRepository extends PagingAndSortingRepository<Genre, Long> {
}
