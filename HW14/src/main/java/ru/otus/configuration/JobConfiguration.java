package ru.otus.configuration;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.HibernateItemWriter;
import org.springframework.batch.item.database.builder.HibernateItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.model.BookEntity;

import java.util.Collections;

@RequiredArgsConstructor
@Configuration
public class JobConfiguration {

	public final static String IMPORT_BOOKS_JOB = "importBooksJob";

	private final static Integer CHUNK_SIZE = 3;

	private final JobBuilderFactory jobBuilderFactory;

	private final StepBuilderFactory stepBuilderFactory;

	@StepScope
	@Bean
	public MongoItemReader<Book> reader(MongoOperations mongoOperations) {
		return new MongoItemReaderBuilder<Book>()
				.name("bookItemReader")
				.targetType(Book.class)
				.template(mongoOperations)
				.jsonQuery("{}")
				.collection("books")
				.sorts(Collections.emptyMap())
				.build();
	}

//	@StepScope
//	@Bean
//	public HibernateCursorItemReader<BookEntity> reader(SessionFactory sessionFactory) {
//		return new HibernateCursorItemReaderBuilder<BookEntity>()
//				.sessionFactory(sessionFactory)
//				.useStatelessSession(true)
//				.name("bookItemReader")
//				.build();
//	}

	@StepScope
	@Bean
	public ItemProcessor<BookEntity, Book> processor() {
		return book -> new Book(book.getTitle(), new Genre(book.getGenre().getName()));
	}

//	@StepScope
//	@Bean
//	public MongoItemWriter<Book> writer(MongoOperations mongoOperations) {
//		return new MongoItemWriterBuilder<Book>()
//				.collection("books")
//				.template(mongoOperations)
//				.build();
//	}

	@StepScope
	@Bean
	public HibernateItemWriter<BookEntity> writer(SessionFactory sessionFactory) {
		return new HibernateItemWriterBuilder<BookEntity>()
				.sessionFactory(sessionFactory)
				.build();
	}

	@Bean
	public Step importBooksFromMongoDBStep(ItemReader<Book> reader, ItemProcessor processor, ItemWriter<BookEntity> writer) {
		return stepBuilderFactory.get("importBooksFromMongoDBStep")
				.chunk(CHUNK_SIZE)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}

	@Bean
	public Job importBooksJob(Step importFromMongoDB) {
		return jobBuilderFactory.get(IMPORT_BOOKS_JOB)
				.incrementer(new RunIdIncrementer())
				.flow(importFromMongoDB)
				.end()
				.build();
	}
}
