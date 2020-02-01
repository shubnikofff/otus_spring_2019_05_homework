package ru.otus.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateCommentRequest {

	private String user;

	private String text;

	private String bookId;
}
