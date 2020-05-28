package ru.otus.compositeservice.feign;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.otus.compositeservice.dto.exchange.ExchangeRequestDto;

import java.util.Collection;
import java.util.Collections;

@Component
public class ExchangeServiceFallback implements ExchangeServiceProxy {
	@Override
	public ResponseEntity<ExchangeRequestDto> getRequestById(String id) {
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<Collection<ExchangeRequestDto>> getRequestsByUsername(String username) {
		return ResponseEntity.ok(Collections.emptyList());
	}

	@Override
	public ResponseEntity<Collection<ExchangeRequestDto>> getRequestsByBookIds(Collection<String> ids) {
		return ResponseEntity.ok(Collections.emptyList());
	}

	@Override
	public ResponseEntity<?> deleteRequest(String id) {
		return ResponseEntity.noContent().build();
	}
}
