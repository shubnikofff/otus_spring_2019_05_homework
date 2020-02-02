package ru.otus.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SaveBookRequest {

	private String title;

	private String genre;

	private List<String> authors;
}
