package ru.otus.application.stringifier;

import org.springframework.stereotype.Service;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Options;
import ru.otus.domain.service.Stringifier;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookStringifier implements Stringifier<Book> {
	@Override
	public String stringify(Book book) {
		return book.getTitle();
	}

	@Override
	public String stringify(List<Book> list) {
		return list.stream()
				.map(book -> " - \"" + book.getTitle() + "\", " + book.getGenre().getName() + " - " + stringifyAuthorList(book.getAuthors()))
				.collect(Collectors.joining("\n"));
	}

	@Override
	public String stringify(Options<Book> options) {
		return options.stream()
				.map(entry -> entry.getKey() + " - " + entry.getValue().getTitle())
				.collect(Collectors.joining("\n"));
	}

	private String stringifyAuthorList(List<Author> authorList) {
		return authorList.stream().map(Author::getName).collect(Collectors.joining(", "));
	}
}
