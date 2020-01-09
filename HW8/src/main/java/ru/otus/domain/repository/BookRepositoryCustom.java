package ru.otus.domain.repository;

import ru.otus.domain.model.Book;

import java.util.List;

public interface BookRepositoryCustom {

	List<Book> findByAuthorName(String name);
}
