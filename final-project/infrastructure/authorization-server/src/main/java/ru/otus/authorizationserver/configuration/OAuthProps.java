package ru.otus.authorizationserver.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "application.oauth2")
public class OAuthProps {

	private String clientId;

	private String clientSecret;

	private String signingKey;
}
