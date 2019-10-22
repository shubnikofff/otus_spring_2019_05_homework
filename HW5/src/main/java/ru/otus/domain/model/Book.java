package ru.otus.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Book {
	private final Long id;
	private final String title;
	private final List<Author> authors;
	private final Genre genre;
}
