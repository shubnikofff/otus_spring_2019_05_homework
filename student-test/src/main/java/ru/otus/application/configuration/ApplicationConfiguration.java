package ru.otus.application.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.io.InputStream;

@Configuration
public class ApplicationConfiguration {

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/i18n/bundle");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public InputStream inputStream() {
		return  System.in;
	}
}
