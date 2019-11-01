package ru.otus.application.service.frontend;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.otus.domain.exception.OperationException;
import ru.otus.domain.model.Author;
import ru.otus.domain.repository.AuthorRepository;
import ru.otus.domain.service.IOService;
import ru.otus.domain.service.Options;
import ru.otus.domain.service.Stringifier;
import ru.otus.domain.service.frontend.AuthorFrontend;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class AuthorFrontendImplementation implements AuthorFrontend {
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
	@Transactional(rollbackOn = OperationException.class)
	public void create(String name) throws OperationException {
		try {
			repository.save(new Author(null, name));
		} catch (DataIntegrityViolationException e) {
			throw new OperationException("Author with that name already exists", e);
		}
	}

	@Override
	@Transactional(rollbackOn = OperationException.class)
	public void update(Author author, String name) throws OperationException {
		try {
			repository.save(new Author(author.getId(), name));
		} catch (DataIntegrityViolationException e) {
			throw new OperationException("Author with that name already exists", e);
		}
	}

	@Override
	@Transactional(rollbackOn = OperationException.class)
	public void delete(Author author) throws OperationException {
		try {
			repository.remove(author);
		} catch (DataIntegrityViolationException e) {
			throw new OperationException("Author has books", e);
		}
	}
}
