package ru.otus.compositeservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.compositeservice.dto.PictureMetadataDto;

import java.util.Collection;

@FeignClient(name = "picture-service", fallback = PictureServiceFallback.class)
public interface PictureServiceProxy {

	@GetMapping("/")
	ResponseEntity<Collection<PictureMetadataDto>> getAllByBookId(@RequestParam String bookId);

}
