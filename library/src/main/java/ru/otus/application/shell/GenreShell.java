package ru.otus.application.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.*;
import ru.otus.domain.exception.OperationException;
import ru.otus.domain.model.Genre;
import ru.otus.domain.service.frontend.GenreFrontendService;

@ShellComponent
@ShellCommandGroup("Genres")
public class GenreShell {
	private final GenreFrontendService frontend;
	private Genre currentGenre;

	public GenreShell(GenreFrontendService frontend) {
		this.frontend = frontend;
	}

	@ShellMethod(value = "List all genres", key = {"lg"})
	String listGenres() {
		return frontend.printAll();
	}

	@ShellMethod(value = "Specify genre for operations", key = {"sg"})
	String specify() {
		currentGenre = frontend.getCurrentGenre();
		return "Current genre " + currentGenre.getName();
	}

	@ShellMethod(value = "Create genre", key = {"cg"})
	String create(@ShellOption(help = "Name of the new genre") String name) throws OperationException {
		final int numberOfRowsCreated = frontend.create(name);
		return "Number of rows created " + numberOfRowsCreated;
	}

	@ShellMethod(value = "Update genre", key = {"ug"})
	@ShellMethodAvailability(value = "isGenreIdSpecified")
	String update(@ShellOption(help = "New name of the genre") String name) throws OperationException {
		final int numberOfRowsUpdated = frontend.update(currentGenre, name);
		currentGenre = null;
		return "Number of rows updated " + numberOfRowsUpdated;
	}

	@ShellMethod(value = "Delete genre", key = {"dg"})
	@ShellMethodAvailability(value = "isGenreIdSpecified")
	String delete() throws OperationException {
		final int numberOfRowDeleted = frontend.delete(currentGenre);
		currentGenre = null;
		return "Number of rows deleted " + numberOfRowDeleted;
	}

	private Availability isGenreIdSpecified() {
		return currentGenre == null
				? Availability.unavailable("genre not specified")
				: Availability.available();
	}
}
