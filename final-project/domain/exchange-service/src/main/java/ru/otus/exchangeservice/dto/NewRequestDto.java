package ru.otus.exchangeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@AllArgsConstructor
@Getter
public class NewRequestDto {

	private final String requestedBookId;

	private final Collection<String> offeredBookIds;

	private final String additionalInfo;
}
