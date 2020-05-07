package ru.otus.bookregistry.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.bookregistry.dto.BookDto;
import ru.otus.bookregistry.exception.BookNotFoundException;
import ru.otus.bookregistry.model.Author;
import ru.otus.bookregistry.model.Book;
import ru.otus.bookregistry.model.Genre;
import ru.otus.bookregistry.repository.BookRepository;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;

	@Override
	public Collection<BookDto> getAll() {
		return bookRepository.findAll().stream().map(BookServiceImpl::mapModelToDto).collect(toList());
	}

	@Override
	public BookDto getOne(String id) {
		return bookRepository.findById(id).map(BookServiceImpl::mapModelToDto).orElseThrow(BookNotFoundException::new);
	}

	@Override
	public String create(BookDto bookDto) {
		return bookRepository.save(mapDtoToModel(bookDto)).getId();
	}

	@Override
	public void update(String id, BookDto bookDto) {
		final Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
		book.setTitle(bookDto.getTitle());
		book.setGenre(new Genre(bookDto.getGenre()));
		book.setAuthors(bookDto.getAuthors().stream().map(Author::new).collect(toList()));
		bookRepository.save(book);
	}

	@Override
	public void delete(String id) {
		final Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
		bookRepository.delete(book);
	}

	private static BookDto mapModelToDto(Book book) {
		return new BookDto(
				book.getId(),
				book.getTitle(),
				book.getGenre().getName(),
				book.getAuthors().stream().map(Author::getName).collect(toList())
		);
	}

	private static Book mapDtoToModel(BookDto bookDto) {
		return new Book(
				bookDto.getId(),
				bookDto.getTitle(),
				new Genre(bookDto.getGenre()),
				bookDto.getAuthors().stream().map(Author::new).collect(toList())
		);
	}
}
