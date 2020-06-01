package ru.otus.authorizationserver.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import ru.otus.authorizationserver.service.TokenEnhancerImpl;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	private static final String AUTHORIZED_GRAND_TYPE_PASSWORD = "password";
	private static final String AUTHORIZED_GRAND_TYPE_AUTHORIZATION_CODE = "authorization_code";
	private static final String AUTHORIZED_GRAND_TYPE_REFRESH_TOKEN = "refresh_token";

	private static final String SCOPE_READ = "read";
	private static final String SCOPE_WRITE = "write";

	private final OAuthProps oAuthProps;

	private final AuthenticationManager authenticationManager;

	private final TokenStore tokenStore;

	private final JwtAccessTokenConverter jwtAccessTokenConverter;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new TokenEnhancerImpl(), jwtAccessTokenConverter));
		endpoints.authenticationManager(authenticationManager)
				.tokenEnhancer(tokenEnhancerChain)
				.tokenStore(tokenStore);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient(oAuthProps.getClientId())
				.secret(oAuthProps.getClientSecret())
				.authorizedGrantTypes(
						AUTHORIZED_GRAND_TYPE_PASSWORD,
						AUTHORIZED_GRAND_TYPE_AUTHORIZATION_CODE,
						AUTHORIZED_GRAND_TYPE_REFRESH_TOKEN
				)
				.scopes(SCOPE_READ, SCOPE_WRITE)
				.accessTokenValiditySeconds(oAuthProps.getAccessTokenValiditySeconds())
				.refreshTokenValiditySeconds(oAuthProps.getRefreshTokenValiditySeconds());
	}
}
