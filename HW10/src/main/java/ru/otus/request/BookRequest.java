package ru.otus.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class BookRequest {

	private String title;

	private String genre;

	private List<String> authors;
}
