package ru.otus.application.frontend;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.otus.domain.exception.OperationException;
import ru.otus.domain.model.Genre;
import ru.otus.domain.service.IOService;
import ru.otus.domain.service.Stringifier;
import ru.otus.domain.service.dao.GenreDao;
import ru.otus.domain.service.frontend.GenreFrontendService;

import java.util.List;
import java.util.stream.Collectors;

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
	public String getAll() {
		final List<Genre> allGenres = dao.getAll();
		return stringifier.stringify(allGenres);
	}

	@Override
	public Long chooseOne() {
		final List<Genre> genreList = dao.getAll();
		final String message = "Choose genre from the list:\n" + stringifier.stringify(genreList);
		final List<Long> idList = genreList.stream().map(Genre::getId).collect(Collectors.toList());
		return io.getOneOf(idList, message);
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
	public int update(Long id, String name) throws OperationException {
		try {
			return dao.save(new Genre(id, name));
		} catch (DuplicateKeyException e) {
			throw new OperationException("Operation failed: name is already in use", e);
		}
	}

	@Override
	public int delete(Long id) throws OperationException {
		try {
			return dao.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new OperationException("Operation failed: genre assigned to the book", e);
		}
	}
}
