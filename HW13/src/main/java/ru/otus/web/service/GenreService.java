package ru.otus.web.service;

import org.springframework.security.access.annotation.Secured;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.web.request.UpdateGenreRequest;

import java.util.List;
import java.util.Optional;

public interface GenreService {

	List<Genre> getAllGenres();

	Optional<Genre> getGenre(String name);

	List<Book> getBooksOfGenre(Genre genre);

	@Secured("ROLE_ADMIN")
	Genre updateGenre(Genre genre, UpdateGenreRequest request);
}
