package ru.otus.service;

import ru.otus.domain.model.Author;
import ru.otus.request.AuthorRequest;

import java.util.List;

public interface AuthorService {

	List<Author> getAll();

	void update(String name, AuthorRequest request);
}
