package ru.otus.bookregistry.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document(collection = "books")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {

	@Id
	private String id;

	private String title;

	private Genre genre;

	private Collection<Author> authors;

	private String owner;
}
