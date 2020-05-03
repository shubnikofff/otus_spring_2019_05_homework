package ru.otus.utilityservice.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import static ru.otus.utilityservice.configuration.JobConfiguration.DB_INITIALISATION_JOB;

@RequiredArgsConstructor
@ShellComponent
public class BatchShell {

	private final JobOperator jobOperator;

	@SneakyThrows
	@ShellMethod(value = "initDatabase", key = "i")
	public void start() {
		jobOperator.start(DB_INITIALISATION_JOB, "");
	}

	@SneakyThrows
	@ShellMethod(value = "restartDbInitialisation", key = "r")
	public void restart() {
		jobOperator.startNextInstance(DB_INITIALISATION_JOB);
	}
}
