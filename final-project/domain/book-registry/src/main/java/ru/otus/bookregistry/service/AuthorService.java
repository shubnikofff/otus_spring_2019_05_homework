package ru.otus.bookregistry.service;

import ru.otus.bookregistry.dto.AuthorDto;

import java.util.Collection;

public interface AuthorService {

	Collection<AuthorDto> getAll();

	void update(String id, AuthorDto authorDto);
}
