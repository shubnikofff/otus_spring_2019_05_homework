package ru.otus.pictureservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class PictureServiceApplication implements CommandLineRunner {

	private final GridFsOperations gridFsOperations;

	public PictureServiceApplication(GridFsOperations gridFsOperations) {
		this.gridFsOperations = gridFsOperations;
	}

	public static void main(String[] args) {
		SpringApplication.run(PictureServiceApplication.class, args);
	}

	@Override
	public void run(String... args) {
		gridFsOperations.delete(new Query());
	}
}
