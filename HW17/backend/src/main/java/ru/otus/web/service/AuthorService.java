package ru.otus.web.service;

import ru.otus.domain.Author;
import ru.otus.web.request.UpdateAuthorRequest;

import java.util.List;

public interface AuthorService {

	List<Author> getAll();

	void update(String name, UpdateAuthorRequest request);
}
