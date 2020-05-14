package ru.otus.compositeservice.feign;

import org.springframework.stereotype.Component;
import ru.otus.compositeservice.dto.CommentDto;

import java.util.Collection;
import java.util.Collections;

@Component
public class ReviewServiceFallback implements ReviewServiceProxy {

	@Override
	public Collection<CommentDto> getByBookId(String bookId) {
		return Collections.emptyList();
	}
}
