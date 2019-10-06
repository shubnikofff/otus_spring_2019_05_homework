package ru.otus.domain.service.frontend;

import ru.otus.domain.exception.OperationException;

public interface GenreFrontendService {
	String getAll();

	Long chooseOne();

	int create(String name) throws OperationException;

	int update(Long id, String name) throws OperationException;

	int delete(Long id) throws OperationException;
}
