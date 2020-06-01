package ru.otus.authorizationserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Profile {

	private final String firstName;

	private final String lastName;

	private final String email;
}
