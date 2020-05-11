package ru.otus.bookregistry.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.bookregistry.model.Author;
import ru.otus.bookregistry.model.Book;
import ru.otus.bookregistry.model.Genre;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@ChangeLog(order = "000")
public class InitDBChangeLog {

	@ChangeSet(order = "000", id = "drop collection books", author = "shubnikofff", runAlways = true)
	public void dropCollectionBooks(MongoOperations mongoOperations) {
		mongoOperations.dropCollection(Book.class);
	}

	@ChangeSet(order = "001", id = "initBooks", author = "shubnikofff", runAlways = true)
	public void initCollectionBooks(MongoOperations mongoOperations) {
		mongoOperations.save(new Book("1", "Lord of the rings", new Genre("Fantasy"), singletonList(new Author("John Ronald Reuel Tolkien"))));
		mongoOperations.save(new Book("2","Crime and Punishment", new Genre("Novel"), singletonList(new Author("Fyodor Mikhailovich Dostoevsky"))));
		mongoOperations.save(new Book("3","War and Peace", new Genre("Novel"), singletonList(new Author("Leo Tolstoy"))));
		mongoOperations.save(new Book("4","Anna Karenina", new Genre("Drama"), singletonList(new Author("Leo Tolstoy"))));
		mongoOperations.save(new Book("5","Golden calf", new Genre("Adventure"), asList(new Author("Ilia Ilf"),  new Author("Eugene Petrov"))));
		mongoOperations.save(new Book("6","Design Patterns", new Genre("Computer science"), asList(new Author("Erich Gamma"),  new Author("Richard Helm"), new Author("Ralph Johnson"),  new Author("John Vlissides"))));
	}
}
