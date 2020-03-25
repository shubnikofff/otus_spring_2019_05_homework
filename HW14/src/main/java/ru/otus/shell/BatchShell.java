package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class BatchShell {

	private final Job importBooksJob;

	private final JobLauncher jobLauncher;

	@SneakyThrows
	@ShellMethod(value = "runJob", key = "run")
	public void runJob() {
		final JobExecution execution = jobLauncher.run(importBooksJob, new JobParameters());
		System.out.println(execution);
	}
}
