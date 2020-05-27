package ru.otus.exchangeservice.service;

import ru.otus.exchangeservice.dto.NewRequestDto;
import ru.otus.exchangeservice.dto.RequestDto;

import java.util.Collection;

public interface RequestService {

	Collection<RequestDto> getByUser(String username);

	Collection<RequestDto> getByRequestedBookIds(Collection<String> bookIds);

	String create(NewRequestDto requestDto, String username);

	void delete(String id);
}
