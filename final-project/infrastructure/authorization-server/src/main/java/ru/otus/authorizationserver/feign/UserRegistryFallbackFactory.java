package ru.otus.authorizationserver.feign;

import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserRegistryFallbackFactory implements FallbackFactory<UserRegistryProxy> {

	@Override
	public UserRegistryProxy create(Throwable cause) {
		return userName -> {
			if (cause instanceof FeignException.NotFound) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		};
	}
}
