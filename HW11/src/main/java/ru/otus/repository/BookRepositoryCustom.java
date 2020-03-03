package ru.otus.repository;

import reactor.core.publisher.Flux;
import ru.otus.domain.model.Book;

public interface BookRepositoryCustom {

	Flux<Book> findByAuthorName(String name);
}
