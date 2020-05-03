package ru.otus.bookregistry.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document(collection = "books")
@NoArgsConstructor
@Getter
public class Book {

	@Id
	private String id;

	private String title;

	private Genre genre;

	private Collection<Author> authors;
}
