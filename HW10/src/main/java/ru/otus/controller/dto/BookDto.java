package ru.otus.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class BookDto {

	private String title;

	private String genre;

	private List<String> authors;
}
