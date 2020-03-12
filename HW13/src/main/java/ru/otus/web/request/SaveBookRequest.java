package ru.otus.web.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class SaveBookRequest {

	private String title;

	private String genre;

	private String authors;
}
