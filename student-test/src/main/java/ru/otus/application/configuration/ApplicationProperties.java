package ru.otus.application.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application")
public class ApplicationProperties {
	private String locale;

	@NestedConfigurationProperty
	private CsvProperties csv;

	@NestedConfigurationProperty
	private SeparatorProperties separator;

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public void setCsv(CsvProperties csv) {
		this.csv = csv;
	}

	public void setSeparator(SeparatorProperties separator) {
		this.separator = separator;
	}

	public String getScvPath() {
		return csv.getPath();
	}

	public String getMainSeparator() {
		return separator.getMain();
	}

	public String getAnswerIdSeparator() {
		return  separator.getAnswerId();
	}
}
