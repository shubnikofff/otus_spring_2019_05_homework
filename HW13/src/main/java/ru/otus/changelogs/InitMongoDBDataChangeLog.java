package ru.otus.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.repository.id.generator.DatabaseSequence;

import java.util.ArrayList;
import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {
	private List<Book> books = new ArrayList<>(6);

	@ChangeSet(order = "000", id = "dropDb", author = "shubnikofff", runAlways = true)
	public void dropDb(MongoDatabase database) {
		database.drop();
	}

	@ChangeSet(order = "001", id = "initBooks", author = "shubnikofff", runAlways = true)
	public void initBooks(MongoTemplate template) {
		books.add(template.save(new Book("Lord of the rings", new Genre("Fantasy"), new Author("John Ronald Reuel Tolkien"))));
		books.add(template.save(new Book("Design Patterns", new Genre("Computer science"), new Author("Erich Gamma"),  new Author("Richard Helm"), new Author("Ralph Johnson"),  new Author("John Vlissides"))));
		books.add(template.save(new Book("Crime and Punishment", new Genre("Novel"), new Author("Fyodor Mikhailovich Dostoevsky"))));
		books.add(template.save(new Book("War and Peace", new Genre("Novel"), new Author("Leo Tolstoy"))));
		books.add(template.save(new Book("Anna Karenina", new Genre("Drama"), new Author("Leo Tolstoy"))));
		books.add(template.save(new Book("Golden calf", new Genre("Adventure"), new Author("Ilia Ilf"),  new Author("Eugene Petrov"))));
	}

	@ChangeSet(order = "002", id = "initComments", author = "shubnikofff", runAlways = true)
	public void initComments(MongoTemplate template) {
		template.save(new Comment(1L, "Martin Fowler", "I'd rather read something about computer science than that.", books.get(0)));
		template.save(new Comment(2L, "John R. R. Tolkien", "Excellent book! I recommend everyone to read.", books.get(0)));
		template.save(new Comment(3L, "John R. R. Tolkien", "Almost fell asleep", books.get(1)));
		template.save(new Comment(4L, "Martin Fowler", "This book is a classic, you should read through it and it should sit on your bookshelf.", books.get(1)));
	}

	@ChangeSet(order = "003", id = "initDatabaseSequences", author = "shubnikofff", runAlways = true)
	public void initDatabaseSequences(MongoTemplate template) {
		template.save(new DatabaseSequence(Comment.SEQUENCE_NAME, 4L));
	}
}
