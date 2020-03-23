package ru.otus.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.domain.Role;
import ru.otus.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByUsername(username).map(user -> User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.roles(user.getRoles().stream().map(Role::getName).toArray(String[]::new))
				.build())
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User %s was not found", username)));
	}
}
