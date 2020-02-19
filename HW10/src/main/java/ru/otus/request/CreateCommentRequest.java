package ru.otus.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateCommentRequest {

	private String user;

	private String text;

	private String bookId;
}
