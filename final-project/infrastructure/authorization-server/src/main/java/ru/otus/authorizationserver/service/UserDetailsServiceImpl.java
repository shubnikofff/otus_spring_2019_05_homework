package ru.otus.authorizationserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.authorizationserver.dto.UserDto;
import ru.otus.authorizationserver.feign.UserRegistryProxy;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRegistryProxy userRegistryProxy;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		final ResponseEntity<UserDto> response = userRegistryProxy.getUser(userName);
		if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
			return response.getBody().toUser();
		}

		throw new UsernameNotFoundException("Invalid username or password");
	}
}
