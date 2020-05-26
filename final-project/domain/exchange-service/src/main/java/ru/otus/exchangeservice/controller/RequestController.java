package ru.otus.exchangeservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.exchangeservice.dto.NewRequestDto;
import ru.otus.exchangeservice.service.RequestService;

@RestController
@RequiredArgsConstructor
public class RequestController {

	private final RequestService requestService;

	@PostMapping("/request")
	public ResponseEntity<String> createRequest(@RequestBody NewRequestDto dto, @RequestHeader("username") String username) {
		return new ResponseEntity<>(requestService.create(dto, username), HttpStatus.CREATED);
	}
	
}
