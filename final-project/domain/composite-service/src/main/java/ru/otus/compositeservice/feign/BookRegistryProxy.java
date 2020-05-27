package ru.otus.compositeservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.otus.compositeservice.dto.BookDto;

import java.util.Collection;
import java.util.Map;

@FeignClient(name = "book-registry", fallbackFactory = BookRegistryFallbackFactory.class)
public interface BookRegistryProxy {

	@GetMapping("/")
	ResponseEntity<Collection<BookDto>> getAllBooks();

	@GetMapping("/{id}")
	ResponseEntity<BookDto> getBook(@PathVariable("id") String id);

	@GetMapping("/multiple/{ids}")
	ResponseEntity<Map<String, BookDto>> getBooks(@PathVariable("ids") Collection<String> ids);

	@GetMapping("/own")
	ResponseEntity<Collection<BookDto>> getOwnBooks(@RequestHeader("username") String username);

	@DeleteMapping("/{id}")
	ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") String id);
}
