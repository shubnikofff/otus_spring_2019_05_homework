package ru.otus.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommentResponse {

	private final String id;

	private final String user;

	private final String text;

	private final String bookId;
}
