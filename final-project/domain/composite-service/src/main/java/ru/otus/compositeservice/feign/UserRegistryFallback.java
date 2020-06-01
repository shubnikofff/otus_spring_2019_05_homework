package ru.otus.compositeservice.feign;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.otus.compositeservice.dto.UserProfileDto;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

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

	@Override
	public ResponseEntity<Map<String, UserProfileDto>> getProfiles(Collection<String> usernames) {
		return new ResponseEntity<>(Collections.emptyMap(), HttpStatus.OK);
	}
}
