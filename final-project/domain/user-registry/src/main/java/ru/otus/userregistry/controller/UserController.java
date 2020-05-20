package ru.otus.userregistry.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.userregistry.dto.UserDto;
import ru.otus.userregistry.service.UserService;

@RestController
@AllArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/")
	ResponseEntity<UserDto> getUser(@RequestParam String userName) {
		return new ResponseEntity<>(userService.getUser(userName), HttpStatus.OK);
	}
}
