package ru.otus.compositeservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.compositeservice.dto.UserProfileDto;

@FeignClient(name = "user-registry", fallback = UserRegistryFallback.class)
public interface UserRegistryProxy {

	@GetMapping("/profile/{username}")
	ResponseEntity<UserProfileDto> getProfile(@PathVariable("username") String username);
}
