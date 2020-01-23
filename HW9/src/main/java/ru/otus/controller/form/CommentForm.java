package ru.otus.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentForm {

	private final String user;

	private final String text;
}
