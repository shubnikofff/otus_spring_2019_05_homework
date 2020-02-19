package ru.otus.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;

public abstract class AbstractControllerTest {

	@MockBean
	private MongoOperations mongoOperations;

	@MockBean
	private BookRepository bookRepository;

	@MockBean
	private CommentRepository commentRepository;

	protected static byte[] objectAsBytes(Object object) {
		try {
			return new ObjectMapper().writeValueAsBytes(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
