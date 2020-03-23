package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Transient;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class Comment {

	@Transient
	public static final String SEQUENCE_NAME = "comments";

	@Id
	private Long id;

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
