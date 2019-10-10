package ru.otus.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Genre {
	private final Long id;
	private final String name;

	public Genre(String name) {
		id = null;
		this.name = name;
	}
}
