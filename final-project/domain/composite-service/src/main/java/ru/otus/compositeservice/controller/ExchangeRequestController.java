package ru.otus.compositeservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.compositeservice.dto.exchange.ExchangeRequestMappedToBookDto;
import ru.otus.compositeservice.service.ExchangeRequestService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class ExchangeRequestController {

	private final ExchangeRequestService exchangeRequestService;

	@GetMapping("/request/outgoing")
	public ResponseEntity<Collection<ExchangeRequestMappedToBookDto>> getOutgoingRequests(@RequestHeader("username") String username) {
		return new ResponseEntity<>(exchangeRequestService.getOutgoingRequests(username), HttpStatus.OK);
	}

	@GetMapping("/request/incoming")
	public ResponseEntity<Collection<ExchangeRequestMappedToBookDto>> getIncomingRequests(@RequestHeader("username") String username) {
		return new ResponseEntity<>(exchangeRequestService.getIncomingRequests(username), HttpStatus.OK);
	}
}
