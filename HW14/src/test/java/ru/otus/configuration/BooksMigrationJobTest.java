package ru.otus.configuration;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.repository.BookMongoRepository;
import ru.otus.repository.BookRelationalRepository;
import ru.otus.repository.CommentMongoRepository;
import ru.otus.repository.CommentRelationalRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.configuration.JobConfiguration.BOOKS_MIGRATION_JOB;

@SpringBootTest
@SpringBatchTest
class BooksMigrationJobTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private JobRepositoryTestUtils jobRepositoryTestUtils;

	@Autowired
	private BookRelationalRepository bookRelationalRepository;

	@Autowired
	private BookMongoRepository bookMongoRepository;

	@Autowired
	private CommentRelationalRepository commentRelationalRepository;

	@Autowired
	private CommentMongoRepository commentMongoRepository;

	@BeforeEach
	void setUp() {
		jobRepositoryTestUtils.removeJobExecutions();
	}

	@Test
	@DisplayName("Test job")
	void testJob() throws Exception {
		val job = jobLauncherTestUtils.getJob();
		assertThat(job)
				.isNotNull()
				.extracting(Job::getName)
				.isEqualTo(BOOKS_MIGRATION_JOB);

		val jobExecution = jobLauncherTestUtils.launchJob();
		assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

		val sourceBooksCount = bookRelationalRepository.count();
		val resultBooksCount = bookMongoRepository.count();
		assertThat(sourceBooksCount).isEqualTo(resultBooksCount);

		val sourceCommentsCount = commentRelationalRepository.count();
		val resultCommentsCount = commentMongoRepository.count();
		assertThat(sourceCommentsCount).isEqualTo(resultCommentsCount);
	}
}
