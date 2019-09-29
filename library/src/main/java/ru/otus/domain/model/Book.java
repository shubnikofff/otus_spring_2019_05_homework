package ru.otus.domain.model;

import java.util.List;

public class Book {
	private final int id;
	private final List<Author> authors;
	private final List<Genre> genres;

	public Book(int id, List<Author> authors, List<Genre> genres) {
		this.id = id;
		this.authors = authors;
		this.genres = genres;
	}

	public int getId() {
		return id;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public List<Genre> getGenres() {
		return genres;
	}
}
