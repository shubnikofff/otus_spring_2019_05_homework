package ru.otus.service;

import ru.otus.domain.model.Author;
import ru.otus.request.UpdateAuthorRequest;

import java.util.List;

public interface AuthorService {

	List<Author> getAll();

	void update(String name, UpdateAuthorRequest request);
}
