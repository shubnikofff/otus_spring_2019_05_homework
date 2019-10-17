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
		currentGenre = frontend.chooseGenre();
		return "Current genre " + currentGenre.getName();
	}

	@ShellMethod(value = "Create genre", key = {"cg"})
	String create(@ShellOption(help = "Name of the new genre") String name) throws OperationException {
		frontend.create(name);
		return "Genre created";
	}

	@ShellMethod(value = "Update genre", key = {"ug"})
	@ShellMethodAvailability(value = "isGenreIdSpecified")
	String update(@ShellOption(help = "New name of the genre") String name) throws OperationException {
		frontend.update(currentGenre, name);
		currentGenre = null;
		return "Genre updated ";
	}

	@ShellMethod(value = "Delete genre", key = {"dg"})
	@ShellMethodAvailability(value = "isGenreIdSpecified")
	String delete() throws OperationException {
		frontend.delete(currentGenre);
		currentGenre = null;
		return "Genre deleted";
	}

	private Availability isGenreIdSpecified() {
		return currentGenre == null
				? Availability.unavailable("genre not specified")
				: Availability.available();
	}
}
