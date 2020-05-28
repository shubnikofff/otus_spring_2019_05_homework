package ru.otus.compositeservice.feign;

import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.otus.compositeservice.dto.BookDto;
import ru.otus.compositeservice.dto.BookStubDto;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Component
public class BookRegistryFallbackFactory implements FallbackFactory<BookRegistryProxy> {

	@Override
	public BookRegistryProxy create(Throwable cause) {
		return new BookRegistryProxy() {
			@Override
			public ResponseEntity<Collection<BookDto>> getAllBooks() {
				return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
			}

			@Override
			public ResponseEntity<BookDto> getBook(String id) {
				if (cause instanceof FeignException.NotFound) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}

				return new ResponseEntity<>(new BookStubDto(id), HttpStatus.OK);
			}

			@Override
			public ResponseEntity<Map<String, BookDto>> getBooks(Collection<String> ids) {
				return new ResponseEntity<>(Collections.emptyMap(), HttpStatus.OK);
			}

			@Override
			public ResponseEntity<Collection<BookDto>> getOwnBooks(String username) {
				return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
			}

			@Override
			public ResponseEntity<?> setOwner(Map<String, String> bookIdUsernameMap) {
				return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
			}

			@Override
			public ResponseEntity<HttpStatus> deleteBook(String id) {
				if (cause instanceof FeignException.NotFound) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}

				return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
			}
		};
	}
}
