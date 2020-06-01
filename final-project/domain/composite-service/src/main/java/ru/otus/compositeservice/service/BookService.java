package ru.otus.compositeservice.service;

import ru.otus.compositeservice.dto.AllBooksItemDto;
import ru.otus.compositeservice.dto.BookCompleteDataDto;

import java.util.Collection;

public interface BookService {

	Collection<AllBooksItemDto> getAllBooks(String username);

	BookCompleteDataDto getBookCompleteData(String bookId, String username);

	void deleteBook(String bookId);

}
