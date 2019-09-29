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

	private static class CsvProperties {
		private String path;

		String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}
	}

	private static class SeparatorProperties {
		private String main;
		private String answerId;

		String getMain() {
			return main;
		}

		public void setMain(String main) {
			this.main = main;
		}

		String getAnswerId() {
			return answerId;
		}

		public void setAnswerId(String answerId) {
			this.answerId = answerId;
		}
	}
}
