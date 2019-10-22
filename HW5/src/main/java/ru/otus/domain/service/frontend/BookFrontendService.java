package ru.otus.domain.service.frontend;

import ru.otus.domain.exception.OperationException;
import ru.otus.domain.model.Book;

import java.util.List;

public interface BookFrontendService {
	String printAll();

	Book chooseBook();

	void create(String title, String genreName, List<String> authorNames) throws OperationException;

	void update(Book book, String title, String genreName, List<String> authorNames) throws OperationException;

	void delete(Book book);
}
