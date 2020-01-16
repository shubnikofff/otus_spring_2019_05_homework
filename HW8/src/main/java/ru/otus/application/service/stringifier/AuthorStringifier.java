package ru.otus.application.service.stringifier;

import org.springframework.stereotype.Service;
import ru.otus.domain.model.Author;
import ru.otus.application.service.frontend.Options;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorStringifier implements Stringifier<Author> {
	@Override
	public String stringify(Author author) {
		return author.getName();
	}

	@Override
	public String stringify(List<Author> list) {
		return list.stream().map(author -> " - " + author.getName()).collect(Collectors.joining("\n"));
	}

	@Override
	public String stringify(Options<Author> options) {
		return options.stream()
				.map(entry -> entry.getKey() + " - " + entry.getValue().getName())
				.collect(Collectors.joining("\n"));
	}
}
