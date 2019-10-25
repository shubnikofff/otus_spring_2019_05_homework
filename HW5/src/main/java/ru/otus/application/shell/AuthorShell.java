package ru.otus.application.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.*;
import ru.otus.domain.exception.OperationException;
import ru.otus.domain.model.Author;
import ru.otus.domain.service.Stringifier;
import ru.otus.domain.service.frontend.AuthorFrontendService;

@ShellComponent
@ShellCommandGroup("Authors")
public class AuthorShell {
	private final AuthorFrontendService frontendService;
	private final Stringifier<Author> stringifier;
	private Author currentAuthor;

	public AuthorShell(AuthorFrontendService frontendService, Stringifier<Author> stringifier) {
		this.frontendService = frontendService;
		this.stringifier = stringifier;
	}

	@ShellMethod(key = {"la"}, value = "List all authors")
	String list() {
		return frontendService.printAll();
	}

	@ShellMethod(key = {"sa"}, value = "Specify author for next operations")
	String specify() {
		currentAuthor = frontendService.chooseOne();
		return "Current author " + stringifier.stringify(currentAuthor);
	}

	@ShellMethod(key = {"ca"}, value = "Create author")
	String create(@ShellOption(help = "Name of the new author") final String name) throws OperationException {
		frontendService.create(name);
		return "Genre created";
	}

	@ShellMethod(key = {"ua"}, value = "Update author")
	@ShellMethodAvailability(value = "isCurrentAuthorSpecified")
	String update(@ShellOption(help = "New author name") final String name) throws OperationException {
		frontendService.update(currentAuthor, name);
		currentAuthor = null;
		return "Author updated ";
	}

	@ShellMethod(key = {"da"}, value = "Delete author")
	@ShellMethodAvailability(value = "isCurrentAuthorSpecified")
	String delete() throws OperationException {
		frontendService.delete(currentAuthor);
		currentAuthor = null;
		return "Author deleted";
	}

	private Availability isCurrentAuthorSpecified() {
		return currentAuthor == null
				? Availability.unavailable("author not specified")
				: Availability.available();
	}
}
