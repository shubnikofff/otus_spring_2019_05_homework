package ru.otus.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	Book findByTitle(String title);
}
