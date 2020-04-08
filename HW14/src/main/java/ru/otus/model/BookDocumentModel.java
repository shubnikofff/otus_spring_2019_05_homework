package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Data
@AllArgsConstructor
@Document(collection = "books")
public class BookDocumentModel {

	@Id
	private String id;

	private String title;

	private GenreDocumentModel genre;

	private Collection<AuthorDocumentModel> authors;
}
