package ru.otus.application.service.frontend;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.exception.OperationException;
import ru.otus.domain.model.Genre;
import ru.otus.domain.repository.GenreRepository;
import ru.otus.domain.service.IOService;
import ru.otus.domain.service.Options;
import ru.otus.domain.service.Stringifier;
import ru.otus.domain.service.frontend.GenreFrontend;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class GenreFrontendImplementation implements GenreFrontend {
	private final GenreRepository repository;
	private final IOService io;
	private final Stringifier<Genre> stringifier;

	@Override
	@Transactional
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
	@Transactional(rollbackOn = OperationException.class)
	public void create(String name) throws OperationException {
		if (repository.findByName(name).isPresent()) {
			throw new OperationException("Genre with that name already exists");
		}

		repository.save(Genre.builder().name(name).build());
	}

	@Override
	@Transactional(rollbackOn = OperationException.class)
	public void update(Genre genre, String name) throws OperationException {
		if (!genre.getName().equals(name) && repository.findByName(name).isPresent()) {
			throw new OperationException(("Genre with that name already exists"));
		}

		genre.setName(name);
		repository.save(genre);
	}

	@Override
	@Transactional(rollbackOn = OperationException.class)
	public void delete(Genre genre) throws OperationException {
		if (!repository.findById(genre.getId()).orElse(Genre.builder().build()).getBooks().isEmpty()) {
			throw new OperationException("Cannot delete a genre which has books");
		}

		repository.delete(genre);
	}
}
