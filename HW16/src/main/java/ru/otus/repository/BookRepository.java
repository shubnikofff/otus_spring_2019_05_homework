package ru.otus.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.domain.Book;

@RepositoryRestResource(path = "book")
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
}
