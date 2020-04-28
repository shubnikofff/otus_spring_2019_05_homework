package ru.otus.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Author;
import ru.otus.repository.AuthorRepository;
import ru.otus.web.exception.AuthorNotFound;
import ru.otus.web.request.UpdateAuthorRequest;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

	private final AuthorRepository repository;

	@Override
	public List<Author> getAll() {
		return repository.findAll();
	}

	@Override
	public void update(String name, UpdateAuthorRequest request) {
		final Author author = repository.findByName(name).orElseThrow(AuthorNotFound::new);
		repository.updateName(author, request.getName());
	}
}
