package ru.otus.compositeservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.otus.compositeservice.dto.BookCompleteDataDto;
import ru.otus.compositeservice.dto.BookDto;
import ru.otus.compositeservice.dto.CommentDto;
import ru.otus.compositeservice.exception.BookNotFoundException;
import ru.otus.compositeservice.exception.BookRegistryUnavailableException;
import ru.otus.compositeservice.feign.BookRegistryProxy;
import ru.otus.compositeservice.feign.ReviewServiceProxy;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookRegistryProxy bookRegistryProxy;

	private final ReviewServiceProxy reviewServiceProxy;

	@Override
	public BookCompleteDataDto getBookCompleteData(String bookId) {
		final BookDto book = bookRegistryProxy.getBook(bookId).orElseThrow(BookNotFoundException::new);
		final Collection<CommentDto> comments = reviewServiceProxy.getByBookId(bookId);

		return new BookCompleteDataDto(
				book.getId(),
				book.getTitle(),
				book.getGenre(),
				book.getAuthors(),
				comments
		);
	}

	@Override
	public void deleteBook(String bookId) {
		final ResponseEntity<HttpStatus> result = bookRegistryProxy.deleteBook(bookId);
		if (result.getStatusCode().is2xxSuccessful()) {
			System.out.println("comments can be deleted");
		}

		if (result.getStatusCode() == HttpStatus.NOT_FOUND) {
			throw new BookNotFoundException();
		}

		if (result.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
			throw new BookRegistryUnavailableException();
		}
	}
}
