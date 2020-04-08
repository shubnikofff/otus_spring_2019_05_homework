package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import static ru.otus.configuration.JobConfiguration.BOOKS_MIGRATION_JOB;

@RequiredArgsConstructor
@ShellComponent
public class BatchShell {

	private final JobOperator jobOperator;

	@SneakyThrows
	@ShellMethod(value = "startMigration", key = "start")
	public void startMigration() {
		jobOperator.start(BOOKS_MIGRATION_JOB, "");
	}

	@SneakyThrows
	@ShellMethod(value = "restartMigration", key = "restart")
	public void restartMigration() {
		jobOperator.startNextInstance(BOOKS_MIGRATION_JOB);
	}
}
