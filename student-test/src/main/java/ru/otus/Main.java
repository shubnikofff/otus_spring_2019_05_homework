package ru.otus;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.application.Application;

public class Main {
	public static void main(String[] args) throws Exception {
		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
		final Application application = context.getBean(Application.class);
		application.run();
	}
}
