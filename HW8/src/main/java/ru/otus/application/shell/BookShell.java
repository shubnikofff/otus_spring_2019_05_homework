package ru.otus.application.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.*;
import ru.otus.domain.exception.OperationException;
import ru.otus.domain.model.Book;
import ru.otus.domain.service.Stringifier;
import ru.otus.domain.service.frontend.BookFrontend;

import java.util.Arrays;

@ShellComponent
@ShellCommandGroup("Books")
public class BookShell {
	private final BookFrontend frontend;
	private final Stringifier<Book> stringifier;
	private Book currentBook;

	public BookShell(BookFrontend frontend, Stringifier<Book> stringifier) {
		this.frontend = frontend;
		this.stringifier = stringifier;
	}

	@ShellMethod(key = {"lb"}, value = "List all books")
	String listBooks() {
		return frontend.printAll();
	}

	@ShellMethod(key = {"sb"}, value = "Specify book for next operations")
	String specify() {
		currentBook = frontend.chooseOne();
		return "Current book \"" + stringifier.stringify(currentBook) + "\"";
	}

	@ShellMethod(key = {"cb"}, value = "Create book")
	String create(
			@ShellOption(help = "Book title") final String title,
			@ShellOption(help = "Genre name") final String genre,
			@ShellOption(help = "Author names separated by comma") final String[] authors
	) throws OperationException {
		frontend.create(title, genre, Arrays.asList(authors));
		return "Book created";
	}

	@ShellMethod(key = {"ub"}, value = "Update book")
	@ShellMethodAvailability(value = "isCurrentBookSpecified")
	String update(
			@ShellOption(help = "Book title") final String title,
			@ShellOption(help = "Genre name") final String genre,
			@ShellOption(help = "Author names separated by comma") final String[] authors
	) throws OperationException {
		frontend.update(currentBook, title, genre, Arrays.asList(authors));
		currentBook = null;
		return "Book updated";
	}

	@ShellMethod(key = {"db"}, value = "Delete book")
	@ShellMethodAvailability(value = "isCurrentBookSpecified")
	String delete() throws OperationException {
		frontend.delete(currentBook);
		currentBook = null;
		return "Book deleted";
	}

	@ShellMethod(key = {"lc"}, value = "List comments of the book")
	@ShellMethodAvailability("isCurrentBookSpecified")
	String listComments() {
		final String comments = frontend.printComments(currentBook);
		currentBook = null;
		return comments;
	}

	@ShellMethod(key = "ac", value = "Add comment")
	@ShellMethodAvailability("isCurrentBookSpecified")
	String addComment(
			@ShellOption(help = "User name", defaultValue = "Anonymous") String user,
			@ShellOption(help = "Comment text") String text
	) throws OperationException {
		frontend.addComment(currentBook, user, text);
		currentBook = null;
		return "Comment added";
	}

	private Availability isCurrentBookSpecified() {
		return currentBook == null
				? Availability.unavailable("book is not specified")
				: Availability.available();
	}
}
