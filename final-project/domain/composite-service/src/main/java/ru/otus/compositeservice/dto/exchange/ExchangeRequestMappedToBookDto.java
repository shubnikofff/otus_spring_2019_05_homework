package ru.otus.compositeservice.dto.exchange;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.otus.compositeservice.dto.BookDto;

import java.util.Collection;

@RequiredArgsConstructor
@Getter
public class ExchangeRequestMappedToBookDto {

	private final String id;

	private final BookDto requestedBook;

	private final Collection<BookDto> offeredBooks;

	private final String additionalInfo;

	private final String requestedDate;
}
