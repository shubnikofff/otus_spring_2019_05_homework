package ru.otus.application.service.frontend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.application.repository.AuthorRepository;
import ru.otus.domain.model.Author;
import ru.otus.domain.service.IOService;
import ru.otus.domain.service.Options;
import ru.otus.domain.service.Stringifier;
import ru.otus.domain.service.frontend.AuthorFrontend;

@Service
@RequiredArgsConstructor
public class AuthorFrontendImpl implements AuthorFrontend {

	private final AuthorRepository repository;
	private final IOService io;
	private final Stringifier<Author> stringifier;

	@Override
	public String printAll() {
		return stringifier.stringify(repository.findAll());
	}

	@Override
	public Author chooseOne() {
		final Options<Author> options = new Options<>(repository.findAll());
		final String message = "Choose author from the list:\n" + stringifier.stringify(options);
		return io.getOneOf(options, message);
	}

	@Override
	public void update(Author author, String name) {
		if (!author.getName().equals(name)) {
			repository.updateName(author, name);
		}
	}
}
