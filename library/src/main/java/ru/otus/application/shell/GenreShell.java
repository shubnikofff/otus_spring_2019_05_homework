package ru.otus.application.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.*;
import ru.otus.domain.exception.OperationException;
import ru.otus.domain.service.frontend.GenreFrontendService;

@ShellComponent
@ShellCommandGroup("Genres")
public class GenreShell {
	private final GenreFrontendService frontend;
	private Long genreId;

	public GenreShell(GenreFrontendService frontend) {
		this.frontend = frontend;
	}

	@ShellMethod(value = "List all genres", key = {"lg"})
	String listGenres() {
		return frontend.getAll();
	}

	@ShellMethod(value = "Specify genre", key = {"sg"})
	String specify() {
		genreId = frontend.chooseOne();
		return "Current genre " + genreId;
	}

	@ShellMethod(value = "Create genre", key = {"cg"})
	String create(@ShellOption(help = "Name of the new genre") String name) throws OperationException {
		final int numberOfRowsCreated = frontend.create(name);
		genreId = null;
		return "Number of rows created " + numberOfRowsCreated;
	}

	@ShellMethod(value = "Update genre", key = {"ug"})
	@ShellMethodAvailability(value = "isGenreIdSpecified")
	String update(@ShellOption(help = "New name of the genre") String name) throws OperationException {
		final int numberOfRowsUpdated = frontend.update(genreId, name);
		genreId = null;
		return "Number of rows updated " + numberOfRowsUpdated;
	}

	@ShellMethod(value = "Delete genre", key = {"dg"})
	@ShellMethodAvailability(value = "isGenreIdSpecified")
	String delete() throws OperationException {
		final int numberOfRowDeleted = frontend.delete(genreId);
		genreId = null;
		return "Number of rows deleted " + numberOfRowDeleted;
	}

	private Availability isGenreIdSpecified() {
		return genreId == null
				? Availability.unavailable("genre not specified")
				: Availability.available();
	}
}
