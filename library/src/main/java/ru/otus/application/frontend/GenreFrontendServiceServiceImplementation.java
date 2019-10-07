package ru.otus.application.frontend;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.otus.domain.exception.OperationException;
import ru.otus.domain.model.Genre;
import ru.otus.domain.model.Options;
import ru.otus.domain.service.IOService;
import ru.otus.domain.service.Stringifier;
import ru.otus.domain.service.dao.GenreDao;
import ru.otus.domain.service.frontend.GenreFrontendService;

import java.util.List;

@Service
public class GenreFrontendServiceServiceImplementation implements GenreFrontendService {
	private final GenreDao dao;
	private final IOService io;
	private final Stringifier<Genre> stringifier;

	public GenreFrontendServiceServiceImplementation(GenreDao dao, IOService io, Stringifier<Genre> stringifier) {
		this.dao = dao;
		this.io = io;
		this.stringifier = stringifier;
	}

	@Override
	public String printAll() {
		final List<Genre> allGenres = dao.getAll();
		return stringifier.stringify(allGenres);
	}

	@Override
	public Genre getCurrentGenre() {
		final Options<Genre> options = new Options<>(dao.getAll());
		final String message = "Choose genre from the list:\n" + stringifier.stringify(options);
		return io.getOneOf(options, message);
	}

	@Override
	public int create(String name) throws OperationException {
		try {
			return dao.save(new Genre(name));
		} catch (DuplicateKeyException e) {
			throw new OperationException("Operation failed: genre with that name already exists", e);
		}
	}

	@Override
	public int update(Genre genre, String name) throws OperationException {
		try {
			return dao.save(new Genre(genre.getId(), name));
		} catch (DuplicateKeyException e) {
			throw new OperationException("Operation failed: name is already in use", e);
		}
	}

	@Override
	public int delete(Genre genre) throws OperationException {
		try {
			return dao.deleteById(genre.getId());
		} catch (DataIntegrityViolationException e) {
			throw new OperationException("Operation failed: genre assigned to the book", e);
		}
	}
}
