package ru.otus.compositeservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AsyncConfiguration {

	private static final int THREADS_NUMBER = 4;

	@Bean(name = "taskExecutor")
	public Executor taskExecutor() {
		return Executors.newFixedThreadPool(THREADS_NUMBER);
	}
}
