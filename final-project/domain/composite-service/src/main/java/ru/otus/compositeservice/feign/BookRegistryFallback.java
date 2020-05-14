package ru.otus.compositeservice.feign;

import org.springframework.stereotype.Component;
import ru.otus.compositeservice.dto.BookDto;

import static java.util.Collections.emptyList;

@Component
public class BookRegistryFallback implements BookRegistryProxy {

	@Override
	public BookDto getBook(String id) {
		return new BookDto(id, "N/A", "N/A", emptyList());
	}
}
