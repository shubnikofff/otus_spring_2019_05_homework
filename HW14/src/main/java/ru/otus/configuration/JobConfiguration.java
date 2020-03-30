package ru.otus.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.model.*;
import ru.otus.repository.BookMongoRepository;
import ru.otus.repository.BookRelationalRepository;
import ru.otus.repository.CommentMongoRepository;
import ru.otus.repository.CommentRelationalRepository;

import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toList;

@EnableBatchProcessing
@RequiredArgsConstructor
@Configuration
public class JobConfiguration {

	public final static String BOOKS_MIGRATION_JOB = "books_migration_job";

	private final static Integer CHUNK_SIZE = 3;

	private final JobBuilderFactory jobBuilderFactory;

	private final StepBuilderFactory stepBuilderFactory;

	@StepScope
	@Bean
	public ItemReader<BookRelationalModel> bookReader(BookRelationalRepository repository) {
		return new RepositoryItemReaderBuilder<BookRelationalModel>()
				.name("bookReader")
				.sorts(emptyMap())
				.repository(repository)
				.methodName("findAll")
				.build();
	}

	@StepScope
	@Bean
	public ItemProcessor<BookRelationalModel, BookDocumentModel> bookProcessor() {
		return item -> new BookDocumentModel(
				null,
				item.getTitle(),
				new GenreDocumentModel(item.getGenre().getName()),
				item.getAuthors().stream().map(author -> new AuthorDocumentModel(author.getName())).collect(toList())
		);
	}

	@StepScope
	@Bean
	public ItemWriter<BookDocumentModel> bookWriter(BookMongoRepository repository) {
		return new RepositoryItemWriterBuilder<BookDocumentModel>()
				.repository(repository)
				.methodName("save")
				.build();
	}

	@Bean
	public Step booksMigrateStep(
			ItemReader<BookRelationalModel> reader,
			ItemProcessor bookProcessor,
			ItemWriter<BookDocumentModel> writer
	) {
		return stepBuilderFactory.get("books_migrate_step")
				.chunk(CHUNK_SIZE)
				.reader(reader)
				.processor(bookProcessor)
				.writer(writer)
				.build();
	}

	@StepScope
	@Bean
	public ItemReader<CommentRelationalModel> commentReader(CommentRelationalRepository repository) {
		return new RepositoryItemReaderBuilder<CommentRelationalModel>()
				.name("commentReader")
				.sorts(emptyMap())
				.repository(repository)
				.methodName("findAll")
				.build();
	}

	@StepScope
	@Bean
	public ItemProcessor<CommentRelationalModel, CommentDocumentModel> commentProcessor(BookMongoRepository repository) {
		return item -> new CommentDocumentModel(
				null,
				item.getUsername(),
				item.getText(),
				repository.findByTitle(item.getBook().getTitle())
		);
	}

	@StepScope
	@Bean
	public ItemWriter<CommentDocumentModel> commentWriter(CommentMongoRepository repository) {
		return new RepositoryItemWriterBuilder<CommentDocumentModel>()
				.repository(repository)
				.methodName("save")
				.build();
	}

	@Bean
	public Step commentMigrateStep(
			ItemReader<CommentRelationalModel> reader,
			ItemProcessor commentProcessor,
			ItemWriter<CommentDocumentModel> writer
	) {
		return stepBuilderFactory.get("comments_migrate_step")
				.chunk(CHUNK_SIZE)
				.reader(reader)
				.processor(commentProcessor)
				.writer(writer)
				.build();
	}

	@Bean
	public Job booksMigrateJob(Step booksMigrateStep, Step commentMigrateStep) {
		return jobBuilderFactory.get(BOOKS_MIGRATION_JOB)
				.incrementer(new RunIdIncrementer())
				.flow(booksMigrateStep)
				.next(commentMigrateStep)
				.end()
				.build();
	}
}
