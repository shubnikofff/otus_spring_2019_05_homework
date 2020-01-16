package ru.otus.application.service.frontend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.application.repository.GenreRepository;
import ru.otus.domain.model.Genre;
import ru.otus.application.service.io.IOService;
import ru.otus.application.service.stringifier.Stringifier;

@Service
@RequiredArgsConstructor
public class GenreFrontendImpl implements GenreFrontend {

	private final GenreRepository repository;
	private final IOService io;
	private final Stringifier<Genre> stringifier;

	@Override
	public String printAll() {
		return stringifier.stringify(repository.findAll());
	}

	@Override
	public Genre chooseOne() {
		final Options<Genre> options = new Options<>(repository.findAll());
		final String message = "Choose genre from the list:\n" + stringifier.stringify(options);
		return io.getOneOf(options, message);
	}

	@Override
	public void update(Genre genre, String name) {
		if (!genre.getName().equals(name)) {
			repository.updateName(genre, name);
		}
	}
}
