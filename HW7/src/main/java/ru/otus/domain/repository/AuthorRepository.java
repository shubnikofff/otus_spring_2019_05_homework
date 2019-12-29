package ru.otus.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.model.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

	Author findByName(String name);

	List<Author> findByNameIn(List<String> names);
}
