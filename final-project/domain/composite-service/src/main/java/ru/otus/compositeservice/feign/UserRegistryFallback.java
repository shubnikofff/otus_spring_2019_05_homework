package ru.otus.compositeservice.feign;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.otus.compositeservice.dto.UserProfileDto;

@Component
public class UserRegistryFallback implements UserRegistryProxy {
	@Override
	public ResponseEntity<UserProfileDto> getProfile(String username) {
		final UserProfileDto dto = new UserProfileDto(
				"Not available",
				"Not available",
				"Not available");

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
}
