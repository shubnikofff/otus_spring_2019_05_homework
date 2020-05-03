package ru.otus.utilityservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
@NoArgsConstructor
@Getter
public class Comment {

	@Id
	private String id;

	private String username;

	private String text;

	@DBRef
	private Book book;

	public Comment(String username, String text, Book book) {
		this.username = username;
		this.text = text;
		this.book = book;
	}
}
