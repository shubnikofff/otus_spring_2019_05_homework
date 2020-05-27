package ru.otus.compositeservice.service;

import ru.otus.compositeservice.dto.exchange.ExchangeRequestMappedToBookDto;

import java.util.Collection;

public interface ExchangeRequestService {

	Collection<ExchangeRequestMappedToBookDto> getOutgoingRequests(String username);

	Collection<ExchangeRequestMappedToBookDto> getIncomingRequests(String username);
}
