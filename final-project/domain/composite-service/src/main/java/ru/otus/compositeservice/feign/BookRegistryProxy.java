package ru.otus.compositeservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.compositeservice.dto.BookDto;

@FeignClient(name = "book-registry", fallbackFactory = BookRegistryFallbackFactory.class)
public interface BookRegistryProxy {

	@GetMapping("/{id}")
	ResponseEntity<BookDto> getBook(@PathVariable("id") String id);

	@DeleteMapping("/{id}")
	ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") String id);
}
