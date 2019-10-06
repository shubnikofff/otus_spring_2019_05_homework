package ru.otus.application.stringifier;

import org.springframework.stereotype.Service;
import ru.otus.domain.model.Genre;
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
		return genreList.stream().map(genre -> genre.getId() + ". " + genre.getName()).collect(Collectors.joining("\n"));
	}
}
