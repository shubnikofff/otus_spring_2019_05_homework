package ru.otus.domain.model;

import java.util.List;

public class Genre {
	private final int  id;
	private final String name;
	private final List<Book> books;

	public Genre(int id, String name, List<Book> books) {
		this.id = id;
		this.name = name;
		this.books = books;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Book> getBooks() {
		return books;
	}
}
