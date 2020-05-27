package ru.otus.compositeservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.otus.compositeservice.dto.*;
import ru.otus.compositeservice.exception.BookNotFoundException;
import ru.otus.compositeservice.exception.BookRegistryUnavailableException;
import ru.otus.compositeservice.exception.ServiceException;
import ru.otus.compositeservice.feign.BookRegistryProxy;
import ru.otus.compositeservice.feign.PictureServiceProxy;
import ru.otus.compositeservice.feign.ReviewServiceProxy;
import ru.otus.compositeservice.feign.UserRegistryProxy;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
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
		try {
			final CompletableFuture<BookDto> book = getBook(bookId);
			final CompletableFuture<Collection<CommentDto>> comments = getCommentsByBook(bookId);
			final CompletableFuture<Collection<PictureMetadataDto>> pictures = getPicturesByBook(bookId);
			final CompletableFuture<UserProfileDto> userProfile = getUserProfile(username);

			CompletableFuture.allOf(book, comments, pictures, userProfile).join();

			final BookDto bookDto = book.get();

			return new BookCompleteDataDto(
					bookDto.getId(),
					bookDto.getTitle(),
					bookDto.getGenre(),
					bookDto.getAuthors(),
					comments.get(),
					pictures.get(),
					userProfile.get(),
					bookDto.getOwner().equals(username)
			);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Async("taskExecutor")
	CompletableFuture<BookDto> getBook(String id) {
		final ResponseEntity<BookDto> response = bookRegistryProxy.getBook(id);

		if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
			throw new BookNotFoundException();
		}

		return CompletableFuture.completedFuture(response.getBody());
	}

	@Async("taskExecutor")
	public CompletableFuture<Collection<CommentDto>> getCommentsByBook(String bookId) {
		return CompletableFuture.completedFuture(reviewServiceProxy.getCommentsByBookId(bookId).getBody());
	}

	@Async("taskExecutor")
	public CompletableFuture<Collection<PictureMetadataDto>> getPicturesByBook(String bookId) {
		return CompletableFuture.completedFuture(pictureServiceProxy.getPicturesByBookId(bookId).getBody());
	}

	@Async("taskExecutor")
	public CompletableFuture<UserProfileDto> getUserProfile(String username) {
		return CompletableFuture.completedFuture(userRegistryProxy.getProfile(username).getBody());
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
