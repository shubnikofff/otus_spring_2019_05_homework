package ru.otus.compositeservice.feign;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.otus.compositeservice.dto.exchange.ExchangeRequestDto;

import java.util.Collection;
import java.util.Collections;

@Component
public class ExchangeServiceFallback implements ExchangeServiceProxy {
	@Override
	public ResponseEntity<Collection<ExchangeRequestDto>> getRequestsByUsername(String username) {
		return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Collection<ExchangeRequestDto>> getRequestsByBookIds(Collection<String> ids) {
		return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
	}
}
