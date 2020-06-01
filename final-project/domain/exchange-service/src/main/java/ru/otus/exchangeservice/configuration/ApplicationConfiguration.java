package ru.otus.exchangeservice.configuration;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class ApplicationConfiguration {
	private static final String CHANGE_LOGS_SCAN_PACKAGE = "ru.otus.exchangeservice.changelogs";

	@Bean
	public Mongock mongock(MongoTemplate mongoTemplate) {
		return new SpringMongockBuilder(mongoTemplate, CHANGE_LOGS_SCAN_PACKAGE).build();
	}
}
