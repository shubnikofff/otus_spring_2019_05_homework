package ru.otus.compositeservice.feign;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.otus.compositeservice.dto.PictureMetadataDto;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Component
public class PictureServiceFallback implements PictureServiceProxy {

	@Override
	public ResponseEntity<Collection<PictureMetadataDto>> getPicturesByBookId(String bookId) {
		return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<HttpStatus> deleteByBookId(String bookId) {
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<Map<String, Collection<PictureMetadataDto>>> getLastPictures(Collection<String> ids) {
		return ResponseEntity.ok(Collections.emptyMap());
	}
}
