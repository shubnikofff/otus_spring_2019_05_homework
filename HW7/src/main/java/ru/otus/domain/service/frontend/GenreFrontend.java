package ru.otus.domain.service.frontend;

import ru.otus.domain.exception.OperationException;
import ru.otus.domain.model.Genre;

public interface GenreFrontend {
	String printAll();

	Genre chooseOne();

	void create(String name) throws OperationException;

	void update(Genre genre, String name) throws OperationException;

	void delete(Genre genre) throws OperationException;
}
