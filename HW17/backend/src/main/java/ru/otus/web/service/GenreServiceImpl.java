package ru.otus.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Genre;
import ru.otus.repository.GenreRepository;
import ru.otus.web.exception.GenreNotFound;
import ru.otus.web.request.UpdateGenreRequest;

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
	public void update(String name, UpdateGenreRequest request) {
		final Genre genre = repository.findByName(name).orElseThrow(GenreNotFound::new);
		repository.updateName(genre, request.getName());
	}
}
