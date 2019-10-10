package ru.otus.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Author {
	private final int id;
	private final String name;
	private final List<Book> books;
}
