package ru.otus.exchangeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
@Getter
public class RequestDto {

	private final String id;

	private final String requestedBookId;

	private final Collection<String> offeredBookIds;

	private final String additionalInfo;

	private final String user;

	private final String requestDate;
}
