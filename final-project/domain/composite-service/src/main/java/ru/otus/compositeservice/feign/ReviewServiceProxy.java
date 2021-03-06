package ru.otus.compositeservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.compositeservice.dto.CommentDto;

import java.util.Collection;

@FeignClient(name = "review-service", fallback = ReviewServiceFallback.class)
public interface ReviewServiceProxy {

	@GetMapping("/")
	ResponseEntity<Collection<CommentDto>> getCommentsByBookId(@RequestParam String bookId);

	@DeleteMapping("/book/{bookId}")
	ResponseEntity<HttpStatus> deleteByBookId(@PathVariable("bookId") String bookId);
}
