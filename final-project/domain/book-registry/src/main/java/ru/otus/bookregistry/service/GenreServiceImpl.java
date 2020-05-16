package ru.otus.bookregistry.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.bookregistry.dto.GenreDto;
import ru.otus.bookregistry.exception.GenreNotFoundException;
import ru.otus.bookregistry.model.Genre;
import ru.otus.bookregistry.repository.GenreRepository;

import java.util.Collection;
import java.util.Collections;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

	private final GenreRepository genreRepository;

	@HystrixCommand(commandKey = "getAllGenres", fallbackMethod = "getAllFallback")
	@Override
	public Collection<GenreDto> getAll() {
		return genreRepository.findAll().stream().map(genre -> new GenreDto(genre.getName())).collect(toList());
	}

	private Collection<GenreDto> getAllFallback() {
		return Collections.emptyList();
	}

	@HystrixCommand(commandKey = "updateGenre", fallbackMethod = "updateFallback")
	@Override
	public void update(String id, GenreDto genreDto) {
		final Genre genre = genreRepository.findByName(id).orElseThrow(GenreNotFoundException::new);
		genreRepository.updateName(genre, genreDto.getName());
	}

	private void updateFallback() {
	}
}
