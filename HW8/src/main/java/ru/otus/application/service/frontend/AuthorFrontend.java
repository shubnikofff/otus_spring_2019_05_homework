package ru.otus.application.service.frontend;

import ru.otus.domain.model.Author;

public interface AuthorFrontend {

	String printAll();

	Author chooseOne();

	void update(Author author, String name);
}
