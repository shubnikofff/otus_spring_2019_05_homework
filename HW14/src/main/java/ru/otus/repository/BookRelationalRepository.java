package ru.otus.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.otus.model.BookRelationalModel;

public interface BookRelationalRepository extends PagingAndSortingRepository<BookRelationalModel, Long> {
}
