package ru.otus.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "comments")
public class Comment {
	@Id
	private String id;

	private String user;

	private String text;

	@DBRef
	private Book book;

	public Comment(String user, String text, Book book) {
		this.user = user;
		this.text = text;
		this.book = book;
	}
}
