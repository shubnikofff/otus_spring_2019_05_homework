package ru.otus.compositeservice.dto;

import java.util.Collections;

public class BookStubDto extends BookDto {
	public BookStubDto(String id) {
		super(id, "Not available", "Not available", Collections.emptyList());
	}
}
