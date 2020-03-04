package ru.otus.web.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SaveBookRequest {

	private String title;

	private String genre;

	private String authors;
}
