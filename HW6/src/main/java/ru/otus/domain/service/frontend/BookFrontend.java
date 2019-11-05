package ru.otus.domain.service.frontend;

import ru.otus.domain.exception.OperationException;
import ru.otus.domain.model.Book;

import javax.management.OperationsException;
import java.util.List;

public interface BookFrontend {
	String printAll();

	String printComments(Book book);

	Book chooseOne();

	void create(String title, String genreName, List<String> authorNames) throws OperationException;

	void update(Book book, String title, String genreName, List<String> authorNames) throws OperationException;

	void delete(Book book) throws OperationException;

	void addComment(Book book, String name, String text) throws OperationsException;
}
