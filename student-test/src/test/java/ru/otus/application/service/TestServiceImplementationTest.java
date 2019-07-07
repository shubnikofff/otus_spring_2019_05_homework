package ru.otus.application.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.domain.model.Answer;
import ru.otus.domain.model.Question;
import ru.otus.domain.service.TestService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestServiceImplementationTest {
	@Autowired
	private TestService testService;

	@Test
	public void testIsAnswerValidWithNoAnswers() {
		Question question = new Question("Question", "", new ArrayList<>(0));
		assertTrue(testService.isAnswerValid(question, ""));
	}

	@Test
	public void testIsAnswerValidWithValidAnswer() {
		Question question = makeQuestion();
		assertTrue(testService.isAnswerValid(question, "2"));
	}

	@Test
	public void testIsAnswerValidWithInvalidAnswer() {
		Question question = makeQuestion();
		assertFalse(testService.isAnswerValid(question, "3"));
	}

	@Test
	public void testGetSuccessPercentage() {
		ru.otus.domain.model.Test test = new ru.otus.domain.model.Test("FirstName", "LastName");
		test.setAnswer(makeQuestion(), "1");
		test.setAnswer(makeQuestion(), "2");
		assertEquals(50.0, testService.getSuccessPercentage(test), 0);
	}

	private Question makeQuestion() {
		List<Answer> answers = new ArrayList<>(2);
		answers.add(new Answer("1", "Answer one"));
		answers.add(new Answer("2", "Answer two"));
		return new Question("Question", "1", answers);
	}
}
