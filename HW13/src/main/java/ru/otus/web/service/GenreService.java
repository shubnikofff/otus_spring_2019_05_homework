package ru.otus.web.service;

import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.web.request.UpdateGenreRequest;

import java.util.List;
import java.util.Optional;

public interface GenreService {

	List<Genre> getAllGenres();

	Optional<Genre> getGenre(String name);

	List<Book> getBooksOfGenre(Genre genre);

	Genre updateGenre(Genre genre, UpdateGenreRequest request);
}
