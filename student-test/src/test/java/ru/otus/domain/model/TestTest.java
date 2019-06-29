package ru.otus.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestTest {

	private ru.otus.domain.model.Test testModel;

	@BeforeEach
	void setUp() {
		testModel = new ru.otus.domain.model.Test("FirstName", "LastName");
	}

	@Test
	void getFirstName() {
		assertEquals("FirstName", testModel.getFirstName());
	}

	@Test
	void getLastName() {
		assertEquals("LastName", testModel.getLastName());
	}

	@Test
	void getSuccessPercentage() {
		List<Answer> answerList = new ArrayList<>();
		answerList.add(new Answer("1", "answer1"));
		answerList.add(new Answer("2", "answer2"));
		Question question = new Question("question", "1", answerList);
		testModel.setAnswer(question, "1");
		assertEquals(100.0, testModel.getSuccessPercentage());
	}
}
