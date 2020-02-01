package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.model.Author;
import ru.otus.exception.AuthorNotFound;
import ru.otus.repository.AuthorRepository;
import ru.otus.request.AuthorRequest;

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
	public void update(String name, AuthorRequest request) {
		final Author author = repository.findByName(name).orElseThrow(AuthorNotFound::new);
		repository.updateName(author, request.getName());
	}
}
