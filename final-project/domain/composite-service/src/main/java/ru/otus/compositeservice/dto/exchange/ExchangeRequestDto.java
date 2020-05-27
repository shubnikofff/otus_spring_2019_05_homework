package ru.otus.compositeservice.dto.exchange;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@AllArgsConstructor
@Getter
public class ExchangeRequestDto {

	private final String id;

	private final String requestedBookId;

	private final Collection<String> offeredBookIds;

	private final String additionalInfo;

	private final String user;

	private final String requestDate;
}
