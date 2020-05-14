package ru.otus.compositeservice.service;

import ru.otus.compositeservice.dto.BookCompleteDataDto;

public interface BookService {

	BookCompleteDataDto getBookCompleteData(String bookId);

}
