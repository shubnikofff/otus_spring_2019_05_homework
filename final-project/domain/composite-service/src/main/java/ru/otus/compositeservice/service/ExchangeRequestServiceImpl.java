package ru.otus.compositeservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.compositeservice.dto.BookDto;
import ru.otus.compositeservice.dto.exchange.ExchangeRequestDto;
import ru.otus.compositeservice.dto.exchange.ExchangeRequestMappedToBookDto;
import ru.otus.compositeservice.feign.BookRegistryProxy;
import ru.otus.compositeservice.feign.ExchangeServiceProxy;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ExchangeRequestServiceImpl implements ExchangeRequestService {

	private final BookRegistryProxy bookRegistryProxy;

	private final ExchangeServiceProxy exchangeServiceProxy;

	@Override
	public Collection<ExchangeRequestMappedToBookDto> getOutgoingRequests(String username) {
		final Collection<ExchangeRequestDto> requests = exchangeServiceProxy.getRequestsByUsername(username).getBody();
		return requests != null ? mapBooksToRequests(requests) : emptyList();
	}

	@Override
	public Collection<ExchangeRequestMappedToBookDto> getIncomingRequests(String username) {

		final List<String> bookIds = Optional.ofNullable(bookRegistryProxy.getOwnBooks(username).getBody())
				.map(body -> body.stream().map(BookDto::getId).collect(toList()))
				.orElse(emptyList());

		final Collection<ExchangeRequestDto> requests = exchangeServiceProxy.getRequestsByBookIds(bookIds).getBody();

		return Optional.ofNullable(requests).map(this::mapBooksToRequests).orElse(emptyList());
	}

	private Collection<ExchangeRequestMappedToBookDto> mapBooksToRequests(Collection<ExchangeRequestDto> requests) {
		final Set<String> bookIds = new HashSet<>();

		requests.forEach(request -> {
			bookIds.add(request.getRequestedBookId());
			bookIds.addAll(request.getOfferedBookIds());
		});

		final Map<String, BookDto> books = bookRegistryProxy.getBooks(bookIds).getBody();

		return requests.stream().map(request -> new ExchangeRequestMappedToBookDto(
				request.getId(),
				Objects.requireNonNull(books).get(request.getRequestedBookId()),
				request.getOfferedBookIds().stream().map(books::get).collect(toList()),
				request.getAdditionalInfo(),
				request.getRequestDate()
		)).collect(toList());
	}
}
