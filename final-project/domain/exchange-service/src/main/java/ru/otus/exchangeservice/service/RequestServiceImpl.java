package ru.otus.exchangeservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.exchangeservice.dto.NewRequestDto;
import ru.otus.exchangeservice.dto.RequestDto;
import ru.otus.exchangeservice.exception.RequestNotFoundException;
import ru.otus.exchangeservice.model.Request;
import ru.otus.exchangeservice.repository.RequestRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

	private final RequestRepository requestRepository;

	@HystrixCommand(commandKey = "getByUser", defaultFallback = "emptyListFallback")
	@Override
	public Collection<RequestDto> getByUser(String username) {
		return requestRepository.findByUser(username).stream().map(RequestTransformer::toDto).collect(toList());
	}

	@HystrixCommand(commandKey = "getByRequestedBookIds", defaultFallback = "emptyListFallback")
	@Override
	public Collection<RequestDto> getByRequestedBookIds(Collection<String> bookIds) {
		return requestRepository.findByRequestedBookIdIn(bookIds).stream().map(RequestTransformer::toDto).collect(toList());
	}

	private Collection<RequestDto> emptyListFallback() {
		return Collections.emptyList();
	}

	@HystrixCommand(commandKey = "creteRequest", fallbackMethod = "createFallback")
	@Override
	public String create(NewRequestDto dto, String username) {
		final Request request = Request.builder()
				.requestedBookId(dto.getRequestedBookId())
				.offeredBookIds(dto.getOfferedBookIds())
				.user(username)
				.requestDate(new Date())
				.additionalInfo(dto.getAdditionalInfo())
				.build();
		return requestRepository.save(request).getId();
	}

	private String createFallback(NewRequestDto dto, String username) {
		return null;
	}

	@HystrixCommand(
			commandKey = "deleteRequest",
			fallbackMethod = "deleteFallback",
			ignoreExceptions = {RequestNotFoundException.class}
	)
	@Override
	public void delete(String id) {
		final Request request = requestRepository.findById(id).orElseThrow(RequestNotFoundException::new);
		requestRepository.delete(request);
	}

	private void deleteFallback(String id) {
	}
}
