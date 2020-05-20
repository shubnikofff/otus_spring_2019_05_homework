package ru.otus.userregistry.service;

import ru.otus.userregistry.dto.UserDto;
import ru.otus.userregistry.model.User;

public interface UserService {

	UserDto getUser(String username);
}
