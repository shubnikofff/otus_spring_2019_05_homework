package ru.otus.compositeservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.compositeservice.dto.BookCompleteDataDto;
import ru.otus.compositeservice.dto.BookDto;
import ru.otus.compositeservice.dto.CommentDto;
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
		final BookDto book = bookRegistryProxy.getBook(bookId);
		final Collection<CommentDto> comments = reviewServiceProxy.getByBookId(bookId);

		return new BookCompleteDataDto(
				book.getId(),
				book.getTitle(),
				book.getGenre(),
				book.getAuthors(),
				comments
		);
	}



}
