package ru.otus.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "comments")
public class Comment {

	@Id
	private String id;

	private String user;

	private String text;

	private String bookId;

	public Comment(String user, String text, String bookId) {
		this.user = user;
		this.text = text;
		this.bookId = bookId;
	}
}
