package ru.otus.compositeservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.compositeservice.dto.exchange.ExchangeRequestDto;

import java.util.Collection;

@FeignClient(name = "exchange-service", fallback = ExchangeServiceFallback.class)
public interface ExchangeServiceProxy {

	@GetMapping("/request/{id}")
	ResponseEntity<ExchangeRequestDto> getRequestById(@PathVariable("id") String id);

	@GetMapping("/request/username/{username}")
	ResponseEntity<Collection<ExchangeRequestDto>> getRequestsByUsername(@PathVariable("username") String username);

	@GetMapping("/request/book/{ids}")
	ResponseEntity<Collection<ExchangeRequestDto>> getRequestsByBookIds(@PathVariable("ids") Collection<String> ids);

	@DeleteMapping("/request/{id}")
	ResponseEntity<?> deleteRequest(@PathVariable("id") String id);
}
