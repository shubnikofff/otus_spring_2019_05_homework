package ru.otus.exchangeservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.exchangeservice.dto.NewRequestDto;
import ru.otus.exchangeservice.dto.RequestDto;
import ru.otus.exchangeservice.service.RequestService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class RequestController {

	private final RequestService requestService;

	@GetMapping("/request/{id}")
	public ResponseEntity<RequestDto> getRequestById(@PathVariable("id") String id) {
		return ResponseEntity.ok(requestService.getById(id));
	}

	@GetMapping("/request/username/{username}")
	public ResponseEntity<Collection<RequestDto>> getRequestsByUsername(@PathVariable("username") String username) {
		return ResponseEntity.ok(requestService.getByUser(username));
	}

	@GetMapping("/request/book/{ids}")
	public ResponseEntity<Collection<RequestDto>> getRequestsByBookIds(@PathVariable("ids") Collection<String> ids) {
		return ResponseEntity.ok(requestService.getByRequestedBookIds(ids));
	}

	@PostMapping("/request")
	public ResponseEntity<String> createRequest(@RequestBody NewRequestDto dto, @RequestHeader("username") String username) {
		return new ResponseEntity<>(requestService.create(dto, username), HttpStatus.CREATED);
	}

	@DeleteMapping("/request/{id}")
	public ResponseEntity<?> deleteRequest(@PathVariable("id") String id) {
		requestService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
