package ru.otus.exchangeservice.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.exchangeservice.model.Request;

import java.util.Arrays;
import java.util.Date;

@ChangeLog(order = "000")
public class InitDBChangeLog {

	@ChangeSet(order = "000", id = "drop collection requests", author = "shubnikofff", runAlways = true)
	public void dropCollectionBooks(MongoOperations mongoOperations) {
		mongoOperations.dropCollection(Request.class);
	}

	@ChangeSet(order = "001", id = "initCollectionRequests", author = "shubnikofff", runAlways = true)
	public void initCollectionRequests(MongoOperations mongoOperations) {
		mongoOperations.save(new Request("1", "6", Arrays.asList("1", "2"), "I really want this book", "fowler", new Date()));
	}
}
