package ru.otus.utilityservice.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonObjectReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.utilityservice.batch.SaveBookExecutionListener;
import ru.otus.utilityservice.model.Book;
import ru.otus.utilityservice.model.BooksJsonRawItem;
import ru.otus.utilityservice.model.Comment;
import ru.otus.utilityservice.repository.BookRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration {

	public static final String DB_INITIALISATION_JOB = "db_initialization_job";

	private static final String SAVE_BOOK_STEP = "save_books_step";

	private static final String BOOK_RESOURCE_PATH = "books.json";

	private static final int CHUNK_SIZE = 3;

	private final JobBuilderFactory jobBuilderFactory;

	private final StepBuilderFactory stepBuilderFactory;

	@StepScope
	@Bean
	public ItemReader<BooksJsonRawItem> bookItemReader() throws Exception {
		final JsonObjectReader<BooksJsonRawItem> jsonObjectReader = new JacksonJsonObjectReader<>(BooksJsonRawItem.class);
		final Resource resource = new ClassPathResource(BOOK_RESOURCE_PATH);
		jsonObjectReader.open(resource);

		return new JsonItemReaderBuilder<BooksJsonRawItem>()
				.jsonObjectReader(jsonObjectReader)
				.resource(resource)
				.name("booksJsonItemReader")
				.build();
	}

	@StepScope
	@Bean
	public ItemProcessor<BooksJsonRawItem, Book> bookItemProcessor() {
		return raw -> new Book(raw.getTitle(), raw.getGenre(), raw.getAuthors());
	}

	@StepScope
	@Bean
	public ItemWriter<Book> bookItemWriter(BookRepository repository) {
		return new RepositoryItemWriterBuilder<Book>()
				.repository(repository)
				.methodName("save")
				.build();
	}

	@Bean
	public Step saveBooksStep(
			ItemReader<BooksJsonRawItem> reader,
			ItemProcessor<BooksJsonRawItem, Book> processor,
			ItemWriter<Book> writer,
			SaveBookExecutionListener listener
	) {
		return stepBuilderFactory.get(SAVE_BOOK_STEP)
				.<BooksJsonRawItem, Book>chunk(CHUNK_SIZE)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.listener(listener)
				.build();
	}

	@StepScope
	@Bean
	public ItemProcessor<BooksJsonRawItem, List<Comment>> commentItemProcessor(BookRepository bookRepository) {


		return raw -> raw.getComments().stream()
				.flatMap(comment -> new Comment(
						comment.getUsername(),
						comment.getText(),
						bookRepository.findByTitle(raw.getTitle()))
				)
				.collect(toList());
	}

	@Bean
	public Job dbInitialisationJob(Step saveBooksStep) {
		return jobBuilderFactory.get(DB_INITIALISATION_JOB)
				.incrementer(new RunIdIncrementer())
				.flow(saveBooksStep)
				.end()
				.build();
	}
}
