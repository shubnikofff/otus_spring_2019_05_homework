package ru.otus.exchangeservice.service;

import ru.otus.exchangeservice.dto.NewRequestDto;
import ru.otus.exchangeservice.dto.RequestDto;

public interface RequestService {

	String create(NewRequestDto requestDto, String username);

	void delete(String id);
}
