package ru.otus.application.frontend;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.otus.domain.exception.OperationException;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Options;
import ru.otus.domain.service.IOService;
import ru.otus.domain.service.Stringifier;
import ru.otus.domain.service.dao.AuthorDao;
import ru.otus.domain.service.frontend.AuthorFrontendService;

@Service
public class AuthorFrontendServiceImplementation implements AuthorFrontendService {
	private final AuthorDao dao;
	private final IOService io;
	private final Stringifier<Author> stringifier;

	public AuthorFrontendServiceImplementation(AuthorDao dao, IOService io, Stringifier<Author> stringifier) {
		this.dao = dao;
		this.io = io;
		this.stringifier = stringifier;
	}

	@Override
	public String printAll() {
		return stringifier.stringify(dao.findAll());
	}

	@Override
	public Author chooseOne() {
		final Options<Author> options = new Options<>(dao.findAll());
		final String message = "Choose author from the list:\n" + stringifier.stringify(options);
		return io.getOneOf(options, message);
	}

	@Override
	public void create(String name) throws OperationException {
		try {
			dao.insert(new Author(null, name));
		} catch (DuplicateKeyException e) {
			throw new OperationException("Author with that name already exists", e);
		}
	}

	@Override
	public void update(Author author, String name) throws OperationException {
		try {
			dao.update(new Author(author.getId(), name));
		} catch (DuplicateKeyException e) {
			throw new OperationException("Author with that name already exists", e);
		}
	}

	@Override
	public void delete(Author author) throws OperationException {
		try {
			dao.deleteById(author.getId());
		} catch (DataIntegrityViolationException e) {
			throw new OperationException("Author has books", e);
		}
	}
}
