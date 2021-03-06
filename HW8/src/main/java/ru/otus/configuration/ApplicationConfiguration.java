package ru.otus.configuration;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ApplicationConfiguration {
	private static final String CHANGE_LOGS_SCAN_PACKAGE = "ru.otus.changelogs";

	@Bean
	public Mongock mongock(MongoDBProps mongoDbProps, MongoClient mongoClient, Environment environment) {
		return new SpringMongockBuilder(mongoClient, mongoDbProps.getDatabase(), CHANGE_LOGS_SCAN_PACKAGE)
				.setSpringEnvironment(environment)
				.build();
	}
}
