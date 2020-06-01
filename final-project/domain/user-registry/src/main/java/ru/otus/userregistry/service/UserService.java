package ru.otus.userregistry.service;

import ru.otus.userregistry.dto.ProfileDto;
import ru.otus.userregistry.dto.UserDto;

import java.util.Collection;
import java.util.Map;

public interface UserService {

	UserDto getUser(String username);

	ProfileDto getProfile(String username);

	Map<String, ProfileDto> getProfiles(Collection<String> usernames);
}
