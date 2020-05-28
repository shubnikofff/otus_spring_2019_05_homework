package ru.otus.compositeservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.compositeservice.dto.BookDto;
import ru.otus.compositeservice.dto.BookWithUserProfileDto;
import ru.otus.compositeservice.dto.UserProfileDto;
import ru.otus.compositeservice.dto.exchange.ExchangeRequestDto;
import ru.otus.compositeservice.dto.exchange.ExchangeRequestMappedToBookDto;
import ru.otus.compositeservice.feign.BookRegistryProxy;
import ru.otus.compositeservice.feign.ExchangeServiceProxy;
import ru.otus.compositeservice.feign.UserRegistryProxy;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ExchangeRequestServiceImpl implements ExchangeRequestService {

	private final BookRegistryProxy bookRegistryProxy;

	private final ExchangeServiceProxy exchangeServiceProxy;

	private final UserRegistryProxy userRegistryProxy;

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

		final Map<String, BookDto> books = getBooks(requests);
		final Map<String, UserProfileDto> userProfiles = getUserProfiles(requests, books);

		return requests.stream().map(request -> {

			final BookDto requestedBook = books.get(request.getRequestedBookId());
			final BookWithUserProfileDto requestedBookDto = new BookWithUserProfileDto(
					requestedBook.getId(),
					requestedBook.getTitle(),
					requestedBook.getGenre(),
					requestedBook.getAuthors(),
					userProfiles.get(requestedBook.getOwner())
			);

			return new ExchangeRequestMappedToBookDto(
					request.getId(),
					requestedBookDto,
					request.getOfferedBookIds().stream().map(books::get).collect(toList()),
					request.getAdditionalInfo(),
					request.getRequestDate(),
					userProfiles.get(request.getUser())
			);
		}).collect(toList());
	}

	private Map<String, BookDto> getBooks(Collection<ExchangeRequestDto> requests) {
		final Set<String> bookIds = new HashSet<>();

		requests.forEach(request -> {
			bookIds.add(request.getRequestedBookId());
			bookIds.addAll(request.getOfferedBookIds());
		});

		return bookRegistryProxy.getBooks(bookIds).getBody();
	}

	private Map<String, UserProfileDto> getUserProfiles(Collection<ExchangeRequestDto> requests, Map<String, BookDto> books) {
		final Set<String> usernames = new HashSet<>();

		requests.forEach(request -> {
			usernames.add(request.getUser());
			usernames.add(books.get(request.getRequestedBookId()).getOwner());
		});

		return userRegistryProxy.getProfiles(usernames).getBody();
	}


}
