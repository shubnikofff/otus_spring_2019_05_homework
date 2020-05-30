package ru.otus.compositeservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.compositeservice.dto.PictureMetadataDto;

import java.util.Collection;
import java.util.Map;

@FeignClient(name = "picture-service", fallback = PictureServiceFallback.class)
public interface PictureServiceProxy {

	@GetMapping("/")
	ResponseEntity<Collection<PictureMetadataDto>> getPicturesByBookId(@RequestParam String bookId);

	@DeleteMapping("/")
	ResponseEntity<HttpStatus> deleteByBookId(@RequestParam String bookId);

	@GetMapping("/last/{ids}")
	ResponseEntity<Map<String, Collection<PictureMetadataDto>>> getLastPictures(@PathVariable("ids") Collection<String> ids);
}
