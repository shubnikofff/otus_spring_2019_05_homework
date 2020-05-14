package ru.otus.compositeservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.compositeservice.dto.BookDto;

@FeignClient(name = "book-registry", fallback = BookRegistryFallback.class)
public interface BookRegistryProxy {

	@GetMapping("/{id}")
	BookDto getBook(@PathVariable("id") String id);
}
