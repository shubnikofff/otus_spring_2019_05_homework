package ru.otus.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

	private Question question;

	@BeforeEach
	void createQuestion() {
		List<Answer> answerList = new ArrayList<>();
		answerList.add(new Answer("1", "answer1"));
		answerList.add(new Answer("2", "answer2"));
		question = new Question("question", "1", answerList);
	}

	@Test
	void isAnswerValidWithValidAnswer() {
		assertTrue(question.isAnswerValid("1"));
	}

	@Test
	void isAnswerValidWithInvalidAnswer() {
		assertFalse(question.isAnswerValid("3"));
	}

	@Test
	void isAnswerValidWithEmptyAnswerList() {
		Question questionWithEmptyAnswerList = new Question("question", "1", new ArrayList<>(0));
		assertTrue(questionWithEmptyAnswerList.isAnswerValid("answer"));
	}

	@Test
	void isAnswerCorrectWithCorrectAnswer() {
		assertTrue(question.isAnswerCorrect("1"));
	}

	@Test
	void isAnswerCorrectWithIncorrectAnswer() {
		assertFalse(question.isAnswerCorrect("10"));
	}

	@Test
	@DisplayName("toString works properly")
	void testToString() {
		assertEquals("question\n" +
						"\t1. answer1\n" +
						"\t2. answer2\n",
				question.toString());
	}
}
