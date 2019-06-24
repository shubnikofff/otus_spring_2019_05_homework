package ru.otus;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.application.Application;

@Configuration
@ComponentScan
class Main {
	public static void main(String[] args) throws Exception {
		final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
		final Application application = context.getBean(Application.class);
		application.run();
	}
}
