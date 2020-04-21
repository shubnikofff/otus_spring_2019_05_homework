package ru.otus.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentResponse {

	private final String id;

	private final String user;

	private final String text;

	private final String bookId;
}
