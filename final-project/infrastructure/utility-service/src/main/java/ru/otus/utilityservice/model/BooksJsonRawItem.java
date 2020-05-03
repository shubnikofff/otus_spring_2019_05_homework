package ru.otus.utilityservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Getter
public class BooksJsonRawItem {

	private String title;

	private Genre genre;

	private Collection<Author> authors;

	private List<Comment> comments;

}
