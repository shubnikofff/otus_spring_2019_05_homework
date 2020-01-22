package ru.otus.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class BookForm {

	private final String title;

	private final String genre;

	private final String authors;
}
