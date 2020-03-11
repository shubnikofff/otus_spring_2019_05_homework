package ru.otus.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.web.request.UpdateGenreRequest;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

	private final BookRepository bookRepository;

	private final GenreRepository genreRepository;

	@Override
	public List<Genre> getAllGenres() {
		return genreRepository.findAll();
	}

	@Override
	public Optional<Genre> getGenre(String name) {
		return genreRepository.findByName(name);
	}

	@Override
	public List<Book> getBooksOfGenre(Genre genre) {
		return bookRepository.findByGenreName(genre.getName());
	}

	@Override
	public Genre updateGenre(Genre genre, UpdateGenreRequest request) {
		return genreRepository.updateName(genre, request.getName());
	}
}
