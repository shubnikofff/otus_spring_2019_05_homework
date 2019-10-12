package ru.otus.application.frontend;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.otus.domain.exception.OperationException;
import ru.otus.domain.model.Genre;
import ru.otus.domain.model.Options;
import ru.otus.domain.service.IOService;
import ru.otus.domain.service.Stringifier;
import ru.otus.domain.service.repository.GenreRepository;
import ru.otus.domain.service.frontend.GenreFrontendService;

import java.util.List;

@Service
public class GenreFrontendServiceServiceImplementation implements GenreFrontendService {
	private final GenreRepository repository;
	private final IOService io;
	private final Stringifier<Genre> stringifier;

	public GenreFrontendServiceServiceImplementation(GenreRepository repository, IOService io, Stringifier<Genre> stringifier) {
		this.repository = repository;
		this.io = io;
		this.stringifier = stringifier;
	}

	@Override
	public String printAll() {
		final List<Genre> allGenres = repository.getAll();
		return stringifier.stringify(allGenres);
	}

	@Override
	public Genre getCurrentGenre() {
		final Options<Genre> options = new Options<>(repository.getAll());
		final String message = "Choose genre from the list:\n" + stringifier.stringify(options);
		return io.getOneOf(options, message);
	}

	@Override
	public int create(String name) throws OperationException {
		try {
			return repository.save(new Genre(null, name));
		} catch (DuplicateKeyException e) {
			throw new OperationException("Operation failed: genre with that name already exists", e);
		}
	}

	@Override
	public int update(Genre genre, String name) throws OperationException {
		try {
			return repository.save(new Genre(genre.getId(), name));
		} catch (DuplicateKeyException e) {
			throw new OperationException("Operation failed: name is already in use", e);
		}
	}

	@Override
	public int delete(Genre genre) throws OperationException {
		try {
			return repository.deleteById(genre.getId());
		} catch (DataIntegrityViolationException e) {
			throw new OperationException("Operation failed: genre assigned to the book", e);
		}
	}
}
