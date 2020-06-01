package ru.otus.authorizationserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.otus.authorizationserver.model.Profile;
import ru.otus.authorizationserver.model.User;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class UserDto {

	private final String username;

	private final String password;

	private final List<String> roles;

	private final ProfileDto profileDto;

	public User toUser() {
		return new User(
				username,
				password,
				roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()),
				new Profile(profileDto.getFirstName(), profileDto.getLastName(), profileDto.getEmail())
		);
	}
}
