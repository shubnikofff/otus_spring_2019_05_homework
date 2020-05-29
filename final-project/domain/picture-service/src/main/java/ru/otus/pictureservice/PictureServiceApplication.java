package ru.otus.pictureservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import ru.otus.pictureservice.service.DbService;

import java.io.IOException;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class PictureServiceApplication implements CommandLineRunner {

	private final DbService dbService;

	public PictureServiceApplication(DbService dbService) {
		this.dbService = dbService;
	}


	public static void main(String[] args) {
		SpringApplication.run(PictureServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws IOException {
		dbService.initDb();
	}
}
