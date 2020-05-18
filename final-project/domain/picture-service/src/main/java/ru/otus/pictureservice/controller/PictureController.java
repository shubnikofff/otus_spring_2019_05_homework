package ru.otus.pictureservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.pictureservice.dto.PictureMetadataDto;
import ru.otus.pictureservice.service.PictureService;

import java.io.IOException;
import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class PictureController {

	private final PictureService pictureService;

	@GetMapping("/{id}")
	public ResponseEntity<Resource> getResource(@PathVariable("id") String id) {
		final GridFsResource resource = pictureService.getResource(id);
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(HttpHeaders.CONTENT_TYPE, resource.getContentType());
		return new ResponseEntity<>(resource, httpHeaders, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<Collection<PictureMetadataDto>> getAllPicturesByBook(@RequestParam String bookId) {
		return new ResponseEntity<>(pictureService.getByBookId(bookId), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<PictureMetadataDto> uploadFile(
			@RequestParam("file") MultipartFile file,
			@RequestParam("bookId") String bookId
	) throws IOException {
		final PictureMetadataDto metaData = pictureService.save(file, bookId);
		return new ResponseEntity<>(metaData, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deletePicture(@PathVariable("id") String id) {
		pictureService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/")
	public ResponseEntity<HttpStatus> deletePicturesByBookId(@RequestParam String bookId) {
		pictureService.deleteByBookId(bookId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
