package ru.otus.exchangeservice.service;

import ru.otus.exchangeservice.dto.RequestDto;
import ru.otus.exchangeservice.model.Request;

public class RequestTransformer {

	public static RequestDto toDto(Request model) {
		return new RequestDto(
				model.getId(),
				model.getRequestedBookId(),
				model.getOfferedBookIds(),
				model.getAdditionalInfo(),
				model.getUser(),
				model.getRequestDate().toString()
		);
	}
}
