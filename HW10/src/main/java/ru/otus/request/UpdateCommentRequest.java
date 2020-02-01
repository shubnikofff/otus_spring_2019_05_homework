package ru.otus.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UpdateCommentRequest {

	private String user;

	private String text;
}
