package ru.otus.pictureservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

@SpringBootApplication
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
