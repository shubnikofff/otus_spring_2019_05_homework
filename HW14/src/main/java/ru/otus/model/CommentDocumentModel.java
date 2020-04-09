package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "comments")
public class CommentDocumentModel {

	@Id
	private String id;

	private String username;

	private String text;

	@DBRef
	private BookDocumentModel book;
}
