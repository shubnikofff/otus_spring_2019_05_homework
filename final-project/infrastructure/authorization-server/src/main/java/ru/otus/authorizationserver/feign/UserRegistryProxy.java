package ru.otus.authorizationserver.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.authorizationserver.dto.UserDto;

@FeignClient(name = "user-registry", fallbackFactory = UserRegistryFallbackFactory.class)
public interface UserRegistryProxy {

	@GetMapping("/{username}")
	ResponseEntity<UserDto> getUser(@PathVariable("username") String userName);
}
