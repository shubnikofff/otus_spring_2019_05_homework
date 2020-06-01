package ru.otus.reviewservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {

	@Id
	private String id;

	private String username;

	private String text;

	private String bookId;
}
