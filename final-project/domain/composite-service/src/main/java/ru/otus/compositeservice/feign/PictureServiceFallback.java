package ru.otus.compositeservice.feign;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.otus.compositeservice.dto.PictureMetadataDto;

import java.util.Collection;
import java.util.Collections;

@Component
public class PictureServiceFallback implements PictureServiceProxy {

	@Override
	public ResponseEntity<Collection<PictureMetadataDto>> getAllByBookId(String bookId) {
		return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
	}
}
