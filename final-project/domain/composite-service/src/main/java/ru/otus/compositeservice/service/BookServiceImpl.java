package ru.otus.compositeservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.otus.compositeservice.dto.BookCompleteDataDto;
import ru.otus.compositeservice.dto.BookDto;
import ru.otus.compositeservice.exception.BookNotFoundException;
import ru.otus.compositeservice.exception.BookRegistryUnavailableException;
import ru.otus.compositeservice.feign.BookRegistryProxy;
import ru.otus.compositeservice.feign.PictureServiceProxy;
import ru.otus.compositeservice.feign.ReviewServiceProxy;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookRegistryProxy bookRegistryProxy;

	private final ReviewServiceProxy reviewServiceProxy;

	private final PictureServiceProxy pictureServiceProxy;

	@Override
	public BookCompleteDataDto getBookCompleteData(String bookId) {
		// TODO use Spring Integration here or message broker
		final ResponseEntity<BookDto> bookRegistryResponse = bookRegistryProxy.getBook(bookId);

		if (bookRegistryResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
			throw new BookNotFoundException();
		}

		final BookDto bookDto = bookRegistryResponse.getBody();

		return new BookCompleteDataDto(
				bookDto.getId(),
				bookDto.getTitle(),
				bookDto.getGenre(),
				bookDto.getAuthors(),
				reviewServiceProxy.getByBookId(bookId).getBody(),
				pictureServiceProxy.getAllByBookId(bookId).getBody()
		);
	}

	@Override
	public void deleteBook(String bookId) {
		final ResponseEntity<HttpStatus> result = bookRegistryProxy.deleteBook(bookId);

		if (result.getStatusCode() == HttpStatus.NOT_FOUND) {
			throw new BookNotFoundException();
		}

		if (result.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
			throw new BookRegistryUnavailableException();
		}

		if (result.getStatusCode().is2xxSuccessful()) {
			reviewServiceProxy.deleteByBookId(bookId);
			pictureServiceProxy.deleteByBookId(bookId);
		}
	}
}
