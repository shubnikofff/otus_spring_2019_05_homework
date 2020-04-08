package ru.otus.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import ru.otus.model.BookDocumentModel;

@RequiredArgsConstructor
@Service
public class BookMigrateExecutionListener implements StepExecutionListener {

	private final MongoOperations mongoOperations;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		if (stepExecution.getStatus() == BatchStatus.STARTED) {
			mongoOperations.dropCollection(BookDocumentModel.class);
		}
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return stepExecution.getExitStatus();
	}
}
