package ru.otus.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentRequest {

	private String user;

	private String text;

	private String bookId;
}
