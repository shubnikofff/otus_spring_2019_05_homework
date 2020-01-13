package ru.otus.application.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.configuration", "ru.otus.application.repository"})
@DisplayName("Author repository")
class AuthorRepositoryTest {

	@Autowired
	private AuthorRepository authorRepository;

	@Test
	@DisplayName("should find all authors")
	void findAll() {
		val authors = authorRepository.findAll();

		assertThat(authors).isNotEmpty();
		assertThat(authors.size()).isEqualTo(new HashSet<>(authors).size());
	}
}
