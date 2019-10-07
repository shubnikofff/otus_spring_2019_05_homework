package ru.otus.domain.model;

import java.util.List;

public class Book {
	private final Long id;
	private final String title;
	private final List<Author> authors;
	private final List<Genre> genres;

	public Book(Long id, String title, List<Author> authors, List<Genre> genres) {
		this.id = id;
		this.title = title;
		this.authors = authors;
		this.genres = genres;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public List<Genre> getGenres() {
		return genres;
	}
}
