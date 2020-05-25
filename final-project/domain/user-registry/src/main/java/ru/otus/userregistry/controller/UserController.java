package ru.otus.userregistry.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.userregistry.dto.ProfileDto;
import ru.otus.userregistry.dto.UserDto;
import ru.otus.userregistry.service.UserService;

@RestController
@AllArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/{username}")
	ResponseEntity<UserDto> getUser(@PathVariable("username") String userName) {
		return new ResponseEntity<>(userService.getUser(userName), HttpStatus.OK);
	}

	@GetMapping("/profile/{username}")
	ResponseEntity<ProfileDto> getProfile(@PathVariable("username") String username) {
		return new ResponseEntity<>(userService.getProfile(username), HttpStatus.OK);
	}
}
