package ru.otus.compositeservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.compositeservice.dto.UserProfileDto;

import java.util.Collection;
import java.util.Map;

@FeignClient(name = "user-registry", fallback = UserRegistryFallback.class)
public interface UserRegistryProxy {

	@GetMapping("/profile/{username}")
	ResponseEntity<UserProfileDto> getProfile(@PathVariable("username") String username);

	@GetMapping("/profiles/{usernames}")
	ResponseEntity<Map<String, UserProfileDto>> getProfiles(@PathVariable("usernames") Collection<String> usernames);
}
