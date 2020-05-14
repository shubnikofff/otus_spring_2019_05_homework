package ru.otus.compositeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentDto {

	private final String id;

	private final String username;

	private final String text;

}
