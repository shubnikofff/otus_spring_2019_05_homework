package ru.otus.authorizationserver.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class User extends org.springframework.security.core.userdetails.User {

	private final Profile profile;


	public User(
			String username,
			String password,
			Collection<? extends GrantedAuthority> authorities,
			Profile profile
	) {
		super(username, password, authorities);
		this.profile = profile;
	}
}
