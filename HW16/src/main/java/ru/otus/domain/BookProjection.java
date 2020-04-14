package ru.otus.domain;

import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;

@Projection(name = "book", types = Book.class)
public interface BookProjection {

	String getTitle();

	Genre getGenre();

	Collection<Author> getAuthors();
}
