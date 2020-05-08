package ru.otus.bookregistry.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.bookregistry.model.Author;
import ru.otus.bookregistry.model.Book;
import ru.otus.bookregistry.model.Genre;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@ChangeLog(order = "001")
public class InitDBChangeLog {
	private final List<Book> books = new ArrayList<>(6);

	@ChangeSet(order = "000", id = "drop books collection", author = "shubnikofff", runAlways = true)
	public void dropCollectionBooks(MongoOperations mongoOperations) {
		mongoOperations.dropCollection(Book.class);
	}

	@ChangeSet(order = "001", id = "initBooks", author = "shubnikofff", runAlways = true)
	public void initCollectionBooks(MongoTemplate template) {
		books.add(template.save(new Book(null, "Lord of the rings", new Genre("Fantasy"), singletonList(new Author("John Ronald Reuel Tolkien")))));
		books.add(template.save(new Book(null,"Crime and Punishment", new Genre("Novel"), singletonList(new Author("Fyodor Mikhailovich Dostoevsky")))));
		books.add(template.save(new Book(null,"War and Peace", new Genre("Novel"), singletonList(new Author("Leo Tolstoy")))));
		books.add(template.save(new Book(null,"Anna Karenina", new Genre("Drama"), singletonList(new Author("Leo Tolstoy")))));
		books.add(template.save(new Book(null,"Golden calf", new Genre("Adventure"), asList(new Author("Ilia Ilf"),  new Author("Eugene Petrov")))));
		books.add(template.save(new Book(null,"Design Patterns", new Genre("Computer science"), asList(new Author("Erich Gamma"),  new Author("Richard Helm"), new Author("Ralph Johnson"),  new Author("John Vlissides")))));
	}
}
