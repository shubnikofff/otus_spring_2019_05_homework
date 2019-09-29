package ru.otus.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.domain.model.Question;
import ru.otus.domain.model.TestResults;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Interview service")
class InterviewServiceTest {

	@Test
	@DisplayName("should return true if answer valid")
	void testIsAnswerValid() {
		final Question question = new Question("question", "answer", new ArrayList<>(0));
		assertTrue(InterviewService.isAnswerValid("answer", question));
	}

	@Test
	@DisplayName("should return success percentage")
	void getSuccessPercentage() {
		final TestResults testResults = new TestResults();
		testResults.addAnswer(new Question("Question1", "answer", new ArrayList<>(0)), "answer");
		testResults.addAnswer(new Question("Question1", "answer", new ArrayList<>(0)), "incorrect answer");

		assertEquals(InterviewService.getSuccessPercentage(testResults), 50.0);
	}
}
