package ru.otus.application.service.frontend;

import ru.otus.domain.model.Genre;

public interface GenreFrontend {

	String printAll();

	Genre chooseOne();

	void update(Genre genre, String name);
}
