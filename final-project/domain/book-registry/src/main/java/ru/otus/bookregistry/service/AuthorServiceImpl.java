package ru.otus.bookregistry.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.bookregistry.dto.AuthorDto;
import ru.otus.bookregistry.exception.AuthorNotFoundException;
import ru.otus.bookregistry.model.Author;
import ru.otus.bookregistry.repository.AuthorRepository;

import java.util.Collection;
import java.util.Collections;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

	private final AuthorRepository authorRepository;

	@HystrixCommand(commandKey = "getAllAuthors", fallbackMethod = "getAllFallback")
	@Override
	public Collection<AuthorDto> getAll() {
		return authorRepository.findAll().stream().map(author -> new AuthorDto(author.getName())).collect(toList());
	}

	private Collection<AuthorDto> getAllFallback() {
		return Collections.emptyList();
	}

	@HystrixCommand(commandKey = "updateAuthor", fallbackMethod = "updateFallback")
	@Override
	public void update(String id, AuthorDto authorDto) {
		final Author author = authorRepository.findById(id).orElseThrow(AuthorNotFoundException::new);
		authorRepository.updateName(author, authorDto.getName());
	}

	private void updateFallback() {
	}
}
