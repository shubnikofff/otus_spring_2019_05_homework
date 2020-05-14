package ru.otus.compositeservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.compositeservice.dto.CommentDto;

import java.util.Collection;

@FeignClient(name = "review-service", fallback = ReviewServiceFallback.class)
public interface ReviewServiceProxy {

	@GetMapping("/")
	Collection<CommentDto> getByBookId(@RequestParam String bookId);

}
