package ru.otus.domain;

import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;

@Projection(name = "allFields", types = Book.class)
public interface BookProjection {

	Long getId();

	String getTitle();

	Genre getGenre();

	Collection<Author> getAuthors();
}
