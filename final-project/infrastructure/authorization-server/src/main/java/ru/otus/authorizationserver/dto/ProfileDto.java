package ru.otus.authorizationserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProfileDto {

	private final String firstName;

	private final String lastName;

	private final String email;
}
