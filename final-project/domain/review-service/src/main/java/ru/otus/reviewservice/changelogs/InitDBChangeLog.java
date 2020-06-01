package ru.otus.reviewservice.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.reviewservice.model.Comment;

@ChangeLog(order = "001")
public class InitDBChangeLog {

	@ChangeSet(order = "000", id = "drop collection comments", author = "shubnikofff", runAlways = true)
	public void dropCollectionBooks(MongoOperations mongoOperations) {
		mongoOperations.dropCollection(Comment.class);
	}

	@ChangeSet(order = "001", id = "initBooks", author = "shubnikofff", runAlways = true)
	public void initCollectionBooks(MongoOperations mongoOperations) {
		mongoOperations.save(new Comment(null, "Vladimir V", "Very good", "1"));
		mongoOperations.save(new Comment(null, "Alexey Petrov", "Interesting book", "1"));
		mongoOperations.save(new Comment(null, "Ivan 666", "Almost fell asleep", "1"));
		mongoOperations.save(new Comment(null, "Vladimir V", "I recommend reading", "2"));
		mongoOperations.save(new Comment(null, "Alexey Petrov", "Boring", "2"));
		mongoOperations.save(new Comment(null, "Anonymous", "Not yet read but I think it will be interesting", "4"));
	}
}
