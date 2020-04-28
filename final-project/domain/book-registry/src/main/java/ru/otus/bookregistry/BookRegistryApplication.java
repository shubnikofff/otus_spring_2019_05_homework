package ru.otus.bookregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BookRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookRegistryApplication.class, args);
	}

}
