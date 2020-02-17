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

@Profile("test")
@ChangeLog(order = "001")
public class InitMongoDBTestDataChangeLog {
	private List<Book> books = new ArrayList<>(3);

	@ChangeSet(order = "000", id = "dropDb", author = "shubnikofff", runAlways = true)
	public void dropDb(MongoDatabase database) {
		database.drop();
	}

	@ChangeSet(order = "001", id = "initBooks", author = "shubnikofff", runAlways = true)
	public void initBooks(MongoTemplate template) {
		books.add(template.save(new Book("Book #1", new Genre("Genre #1"), new Author("Author #1"))));
		books.add(template.save(new Book("Book #2", new Genre("Genre #1"), new Author("Author #2"))));
		books.add(template.save(new Book("Book #3", new Genre("Genre #2"), new Author("Author #1"), new Author("Author #2"))));
	}

	@ChangeSet(order = "002", id = "initComments", author = "shubnikofff", runAlways = true)
	public void initComments(MongoTemplate template) {
		template.save(new Comment("User #1", "Comment #1", books.get(0).getId()));
		template.save(new Comment("User #2", "Comment #1", books.get(0).getId()));
		template.save(new Comment("User #3", "Comment #2", books.get(1).getId()));
	}
}
