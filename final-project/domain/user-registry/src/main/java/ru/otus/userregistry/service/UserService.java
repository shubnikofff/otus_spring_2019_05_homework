package ru.otus.userregistry.service;

import ru.otus.userregistry.dto.ProfileDto;
import ru.otus.userregistry.dto.UserDto;

public interface UserService {

	UserDto getUser(String username);

	ProfileDto getProfile(String username);
}
