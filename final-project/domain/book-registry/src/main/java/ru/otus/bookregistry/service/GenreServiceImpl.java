package ru.otus.bookregistry.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.bookregistry.dto.GenreDto;
import ru.otus.bookregistry.exception.GenreNotFoundException;
import ru.otus.bookregistry.model.Genre;
import ru.otus.bookregistry.repository.GenreRepository;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

	private final GenreRepository genreRepository;

	@Override
	public Collection<GenreDto> getAll() {
		return genreRepository.findAll().stream().map(genre -> new GenreDto(genre.getName())).collect(toList());
	}

	@Override
	public void update(String id, GenreDto genreDto) {
		final Genre genre = genreRepository.findByName(id).orElseThrow(GenreNotFoundException::new);
		genreRepository.updateName(genre, genreDto.getName());
	}
}
