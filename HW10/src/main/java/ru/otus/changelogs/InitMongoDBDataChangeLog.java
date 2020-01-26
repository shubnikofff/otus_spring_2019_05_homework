package ru.otus.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;
import ru.otus.domain.model.Genre;

import java.util.ArrayList;
import java.util.List;

@Profile("prod")
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
		books.add(template.save(new Book("Crime and Punishment", new Genre("Novel"), new Author("Fyodor Mikhailovich Dostoevsky"))));
		books.add(template.save(new Book("War and Peace", new Genre("Novel"), new Author("Leo Tolstoy"))));
		books.add(template.save(new Book("Anna Karenina", new Genre("Drama"), new Author("Leo Tolstoy"))));
		books.add(template.save(new Book("Golden calf", new Genre("Adventure"), new Author("Ilia Ilf"),  new Author("Eugene Petrov"))));
		books.add(template.save(new Book("Design Patterns", new Genre("Computer science"), new Author("Erich Gamma"),  new Author("Richard Helm"), new Author("Ralph Johnson"),  new Author("John Vlissides"))));
	}

	@ChangeSet(order = "002", id = "initComments", author = "shubnikofff", runAlways = true)
	public void initComments(MongoTemplate template) {
		template.save(new Comment("Vladimir V", "Very good", books.get(0)));
		template.save(new Comment("Alexey Petrov", "Interesting book", books.get(0)));
		template.save(new Comment("Ivan 666", "Almost fell asleep", books.get(0)));
		template.save(new Comment("Vladimir V", "I recommend reading", books.get(1)));
		template.save(new Comment("Alexey Petrov", "Boring", books.get(1)));
		template.save(new Comment("Anonymous", "Not yet read but I think it will be interesting", books.get(3)));
	}
}
