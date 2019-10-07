package ru.otus.domain.service.frontend;

import ru.otus.domain.exception.OperationException;
import ru.otus.domain.model.Genre;

public interface GenreFrontendService {
	String printAll();

	Genre getCurrentGenre();

	int create(String name) throws OperationException;

	int update(Genre genre, String name) throws OperationException;

	int delete(Genre genre) throws OperationException;
}
