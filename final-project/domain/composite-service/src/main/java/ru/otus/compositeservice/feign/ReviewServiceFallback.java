package ru.otus.compositeservice.feign;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.otus.compositeservice.dto.CommentDto;

import java.util.Collection;
import java.util.Collections;

@Component
public class ReviewServiceFallback implements ReviewServiceProxy {

	@Override
	public ResponseEntity<Collection<CommentDto>> getByBookId(String bookId) {
		return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<HttpStatus> deleteByBookId(String bookId) {
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
