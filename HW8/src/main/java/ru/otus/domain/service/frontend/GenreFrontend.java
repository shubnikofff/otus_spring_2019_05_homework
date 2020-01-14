package ru.otus.domain.service.frontend;

import ru.otus.domain.model.Genre;

public interface GenreFrontend {

	String printAll();

	Genre chooseOne();

	void update(Genre genre, String name);
}
