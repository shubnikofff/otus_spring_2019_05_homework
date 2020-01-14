package ru.otus.application.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.*;
import ru.otus.domain.exception.OperationException;
import ru.otus.domain.model.Genre;
import ru.otus.domain.service.Stringifier;
import ru.otus.domain.service.frontend.GenreFrontend;

@ShellComponent
@ShellCommandGroup("Genres")
public class GenreShell {
	private final GenreFrontend frontend;
	private final Stringifier<Genre> stringifier;
	private Genre currentGenre;

	public GenreShell(GenreFrontend frontend, Stringifier<Genre> stringifier) {
		this.frontend = frontend;
		this.stringifier = stringifier;
	}

	@ShellMethod(key = "lg", value = "List all genres")
	String list() {
		return frontend.printAll();
	}

	@ShellMethod(key = "sg", value = "Specify genre for next operations")
	String specify() {
		currentGenre = frontend.chooseOne();
		return "Current genre " + stringifier.stringify(currentGenre);
	}

	@ShellMethod(key = "ug", value = "Update genre")
	@ShellMethodAvailability("isCurrentGenreSpecified")
	String update(@ShellOption(help = "New name of the genre") final String name) throws OperationException {
		frontend.update(currentGenre, name);
		currentGenre = null;
		return "Genre updated";
	}

	private Availability isCurrentGenreSpecified() {
		return currentGenre == null
				? Availability.unavailable("genre not specified")
				: Availability.available();
	}
}
