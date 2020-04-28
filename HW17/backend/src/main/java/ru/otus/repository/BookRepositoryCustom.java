package ru.otus.repository;

import ru.otus.domain.Book;

import java.util.List;

public interface BookRepositoryCustom {

	List<Book> findByAuthorName(String name);
}
