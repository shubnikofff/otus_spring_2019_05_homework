package ru.otus.compositeservice.feign;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.otus.compositeservice.dto.BookDto;
import ru.otus.compositeservice.dto.BookStubDto;

import java.util.Optional;

@Component
public class BookRegistryFallbackFactory implements FallbackFactory<BookRegistryProxy> {

	@Override
	public BookRegistryProxy create(Throwable cause) {
		return new BookRegistryProxy() {
			@Override
			public ResponseEntity<BookDto> getBook(String id) {
				if (cause instanceof FeignException.NotFound) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}

				return new ResponseEntity<>(new BookStubDto(id), HttpStatus.OK);
			}

			@Override
			public ResponseEntity<HttpStatus> deleteBook(String id) {
				if (cause instanceof FeignException.NotFound) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}

				if (cause instanceof HystrixRuntimeException) {
					return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
				}

				return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
			}
		};
	}
}
