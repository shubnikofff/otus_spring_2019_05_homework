package ru.otus.compositeservice.dto.exchange;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.otus.compositeservice.dto.BookDto;
import ru.otus.compositeservice.dto.BookWithUserProfileDto;
import ru.otus.compositeservice.dto.UserProfileDto;

import java.util.Collection;

@RequiredArgsConstructor
@Getter
public class ExchangeRequestMappedToBookDto {

	private final String id;

	private final BookWithUserProfileDto requestedBook;

	private final Collection<BookDto> offeredBooks;

	private final String additionalInfo;

	private final String requestedDate;

	private final UserProfileDto user;
}
