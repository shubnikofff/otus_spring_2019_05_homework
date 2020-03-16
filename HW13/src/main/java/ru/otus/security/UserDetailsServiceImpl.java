package ru.otus.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.domain.Role;
import ru.otus.repository.UserRepository;

import java.util.Collection;

import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByUsername(username).map(user -> User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.authorities(rolesToAuthorities(user.getRoles()))
				.build())
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User %s was not found", username)));
	}

	private static Collection<GrantedAuthority> rolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(String.format("ROLE_%s", role.getName()))).collect(toSet());
	}
}
