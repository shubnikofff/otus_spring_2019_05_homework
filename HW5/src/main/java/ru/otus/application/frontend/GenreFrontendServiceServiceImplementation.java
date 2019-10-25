package ru.otus.application.frontend;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.otus.domain.exception.OperationException;
import ru.otus.domain.model.Genre;
import ru.otus.domain.model.Options;
import ru.otus.domain.service.IOService;
import ru.otus.domain.service.Stringifier;
import ru.otus.domain.dao.GenreDao;
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
		final List<Genre> allGenres = dao.findAll();
		return stringifier.stringify(allGenres);
	}

	@Override
	public Genre chooseGenre() {
		final Options<Genre> options = new Options<>(dao.findAll());
		final String message = "Choose genre from the list:\n" + stringifier.stringify(options);
		return io.getOneOf(options, message);
	}

	@Override
	public void create(String name) throws OperationException {
		try {
			dao.insert(new Genre(null, name));
		} catch (DuplicateKeyException e) {
			throw new OperationException("Genre with that name already exists", e);
		}
	}

	@Override
	public void update(Genre genre, String name) throws OperationException {
		try {
			dao.update(new Genre(genre.getId(), name));
		} catch (DuplicateKeyException e) {
			throw new OperationException("Genre with that name already exists", e);
		}
	}

	@Override
	public void delete(Genre genre) throws OperationException {
		try {
			dao.deleteById(genre.getId());
		} catch (DataIntegrityViolationException e) {
			throw new OperationException("Genre assigned to the book", e);
		}
	}
}
