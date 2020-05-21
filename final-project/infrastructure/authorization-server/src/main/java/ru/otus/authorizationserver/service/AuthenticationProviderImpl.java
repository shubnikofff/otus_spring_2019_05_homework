package ru.otus.authorizationserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.authorizationserver.model.User;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationProviderImpl implements AuthenticationProvider {

	private static final String PROFILE = "profile";

	private final UserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		final String userName = authentication.getName();
		final Object userCredentials = authentication.getCredentials();

		if (userName == null || userCredentials == null) {
			return null;
		}

		if (userName.isEmpty() || userCredentials.toString().isEmpty()) {
			return null;
		}

		final User user = (User) userDetailsService.loadUserByUsername(userName);

		if (userName.equalsIgnoreCase(user.getUsername()) && userCredentials.equals(user.getPassword())) {
			final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					user.getUsername(),
					user.getPassword(),
					user.getAuthorities()
			);

			token.setDetails(Collections.singletonMap(PROFILE, user.getProfile()));

			return token;
		}

		throw new UsernameNotFoundException("Invalid username or password");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
