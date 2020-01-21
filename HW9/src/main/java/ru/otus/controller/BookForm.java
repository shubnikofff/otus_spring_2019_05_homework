package ru.otus.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BookForm {

	private final String title;

	private final String genre;

	private final String authors;
}
