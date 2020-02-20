package ru.otus.configuration;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class ApplicationConfiguration {
	private static final String CHANGE_LOGS_SCAN_PACKAGE = "ru.otus.changelogs";

	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create();
	}

	@Bean
	public Mongock mongock(MongoTemplate mongoTemplate, Environment environment) {
		return new SpringMongockBuilder(mongoTemplate, CHANGE_LOGS_SCAN_PACKAGE)
				.setSpringEnvironment(environment)
				.build();
	}
}
