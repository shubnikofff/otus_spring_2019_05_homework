package ru.otus.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.model.BookDocumentModel;

import java.util.ArrayList;
import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {
	private List<BookDocumentModel> bookDocumentModels = new ArrayList<>(6);

	@ChangeSet(order = "000", id = "dropDb", author = "shubnikofff", runAlways = true)
	public void dropDb(MongoDatabase database) {
		database.drop();
	}
}
