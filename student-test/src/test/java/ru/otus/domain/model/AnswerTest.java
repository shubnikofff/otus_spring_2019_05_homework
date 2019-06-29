package ru.otus.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Class Person")
class AnswerTest {
	private Answer answer;

	@BeforeEach
	void setUp() {
		answer = new Answer("1", "answer");
	}

	@Test
	@DisplayName("getId works correctly")
	void getId() {
		assertEquals("1", answer.getId());
	}

	@Test
	@DisplayName("getWording works correctly")
	void getWording() {
		assertEquals("answer", answer.getWording());
	}
}
