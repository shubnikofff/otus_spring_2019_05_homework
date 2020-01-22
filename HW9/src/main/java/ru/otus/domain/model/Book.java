package ru.otus.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "books")
public class Book {
	@Id
	private String id;

	private String title;

	private Genre genre;

	private List<Author> authors;

	public Book(String title, Genre genre, Author... authors) {
		this.title = title;
		this.genre = genre;
		this.authors = Arrays.asList(authors);
	}

	public Book(String title, Genre genre, List<Author> authors) {
		this.title = title;
		this.genre = genre;
		this.authors = authors;
	}
}
