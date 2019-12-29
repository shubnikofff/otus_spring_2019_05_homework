package ru.otus.application.service.stringifier;

import org.springframework.stereotype.Service;
import ru.otus.domain.model.Genre;
import ru.otus.domain.service.Options;
import ru.otus.domain.service.Stringifier;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreStringifier implements Stringifier<Genre> {
	@Override
	public String stringify(Genre genre) {
		return genre.getName();
	}

	@Override
	public String stringify(List<Genre> genreList) {
		return genreList.stream().map(genre -> " - " + genre.getName()).collect(Collectors.joining("\n"));
	}

	@Override
	public String stringify(Options<Genre> options) {
		return options.stream()
				.map(integerGenreEntry -> integerGenreEntry.getKey() + " - " + integerGenreEntry.getValue().getName())
				.collect(Collectors.joining("\n"));
	}
}
