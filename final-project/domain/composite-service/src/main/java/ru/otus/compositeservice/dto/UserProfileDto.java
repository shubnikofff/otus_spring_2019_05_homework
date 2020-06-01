package ru.otus.compositeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserProfileDto {

	private final String firstName;

	private final String lastName;

	private final String email;
}
