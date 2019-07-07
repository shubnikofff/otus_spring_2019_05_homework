package ru.otus.application.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuestionDaoCsvTest {
	private QuestionDaoCsv questionDaoCsv;

	@BeforeEach
	void setUp() {
		questionDaoCsv = new QuestionDaoCsv("csv", "en", ",", "#");
	}

	@Test
	void getQuestions() {
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals(5, questionDaoCsv.getQuestions().size());
		});

	}
}
