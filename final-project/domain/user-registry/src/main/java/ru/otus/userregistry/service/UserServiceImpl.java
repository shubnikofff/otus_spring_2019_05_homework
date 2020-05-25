package ru.otus.userregistry.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.userregistry.dto.ProfileDto;
import ru.otus.userregistry.dto.UserDto;
import ru.otus.userregistry.exception.UserNotFoundException;
import ru.otus.userregistry.model.Profile;
import ru.otus.userregistry.model.Role;
import ru.otus.userregistry.model.User;
import ru.otus.userregistry.repository.UserRepository;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public UserDto getUser(String username) {
		final User user = userRepository.getByUsername(username).orElseThrow(UserNotFoundException::new);
		return new UserDto(
				user.getUsername(),
				user.getPassword(),
				user.getRoles().stream().map(Role::getName).collect(toList()),
				ProfileTransformer.toDto(user.getProfile())
		);
	}

	@Override
	public ProfileDto getProfile(String username) {
		final Profile profile = userRepository.getByUsername(username).orElseThrow(UserNotFoundException::new).getProfile();

		return ProfileTransformer.toDto(profile);
	}
}
