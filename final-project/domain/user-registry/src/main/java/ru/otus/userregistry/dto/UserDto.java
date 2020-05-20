package ru.otus.userregistry.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UserDto {

	private final String username;

	private final String password;

	private final List<String> roles;

	private final ProfileDto profileDto;
}
