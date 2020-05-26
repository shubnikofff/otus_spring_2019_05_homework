package ru.otus.compositeservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.otus.compositeservice.dto.AllBooksItemDto;
import ru.otus.compositeservice.dto.BookCompleteDataDto;
import ru.otus.compositeservice.dto.BookDto;
import ru.otus.compositeservice.exception.BookNotFoundException;
import ru.otus.compositeservice.exception.BookRegistryUnavailableException;
import ru.otus.compositeservice.feign.BookRegistryProxy;
import ru.otus.compositeservice.feign.PictureServiceProxy;
import ru.otus.compositeservice.feign.ReviewServiceProxy;
import ru.otus.compositeservice.feign.UserRegistryProxy;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookRegistryProxy bookRegistryProxy;

	private final ReviewServiceProxy reviewServiceProxy;

	private final PictureServiceProxy pictureServiceProxy;

	private final UserRegistryProxy userRegistryProxy;

	@Override
	public Collection<AllBooksItemDto> getAllBooks(String username) {
		final Collection<BookDto> books = bookRegistryProxy.getAllBooks().getBody();

		return books.stream().map(book -> new AllBooksItemDto(
				book.getId(),
				book.getTitle(),
				book.getGenre(),
				book.getAuthors(),
				null,
				book.getOwner().equals(username)
		)).collect(Collectors.toList());
	}

	@Override
	public BookCompleteDataDto getBookCompleteData(String bookId, String username) {
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
				pictureServiceProxy.getAllByBookId(bookId).getBody(),
				userRegistryProxy.getProfile(bookDto.getOwner()).getBody(),
				bookDto.getOwner().equals(username)
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
