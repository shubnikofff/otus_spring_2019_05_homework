package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.model.Genre;
import ru.otus.exception.GenreNotFound;
import ru.otus.repository.GenreRepository;
import ru.otus.request.GenreRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

	private final GenreRepository repository;

	@Override
	public List<Genre> getAll() {
		return repository.findAll();
	}

	@Override
	public void update(String name, GenreRequest request) {
		final Genre genre = repository.findByName(name).orElseThrow(GenreNotFound::new);
		repository.updateName(genre, request.getName());
	}
}
