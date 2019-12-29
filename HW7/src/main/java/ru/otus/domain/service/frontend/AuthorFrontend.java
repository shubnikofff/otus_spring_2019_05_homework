package ru.otus.domain.service.frontend;

import ru.otus.domain.exception.OperationException;
import ru.otus.domain.model.Author;

public interface AuthorFrontend {
	String printAll();

	Author chooseOne();

	void create(String name) throws OperationException;

	void update(Author author, String name) throws OperationException;

	void delete(Author author) throws OperationException;
}
