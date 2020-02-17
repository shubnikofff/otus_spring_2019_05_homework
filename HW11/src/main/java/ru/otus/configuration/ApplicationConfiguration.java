package ru.otus.configuration;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;

@Configuration
public class ApplicationConfiguration extends AbstractReactiveMongoConfiguration {
	private static final String CHANGE_LOGS_SCAN_PACKAGE = "ru.otus.changelogs";
	private static final String DB_NAME = "library";

	@Bean
	public MongoClient reactiveMongoClient() {
		return MongoClients.create();
	}

	@Bean
	protected String getDatabaseName() {
		return DB_NAME;
	}

	@Bean
	public Mongock mongock(com.mongodb.MongoClient mongoClient, Environment environment) {
		return new SpringMongockBuilder(mongoClient, DB_NAME, CHANGE_LOGS_SCAN_PACKAGE)
				.setSpringEnvironment(environment)
				.build();
	}
}
