package ru.otus.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ru.otus.domain.model.Question;
import ru.otus.domain.service.FrontendService;
import ru.otus.domain.service.IOService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayName("Frontend service")
class FrontendServiceImplementationTest {
	@MockBean
	IOService ioService;

	@Autowired
	private FrontendService frontendService;

	@Test
	@DisplayName("should return first name")
	void testGetFirstName() {
		given(ioService.getInput()).willReturn("FirstName");
		assertEquals("FirstName", frontendService.getFirstName());
	}

	@Test
	@DisplayName("should return last name")
	void testGetLastName() {
		given(ioService.getInput()).willReturn("LastName");
		assertEquals("LastName", frontendService.getLastName());
	}

	@Test
	@DisplayName("should call IOService.print() method on greeting")
	void testGreeting() {
		frontendService.greeting();
		verify(ioService, times(1)).print("You have successfully logged in. Now you can start test.\n");
	}

	@Test
	@DisplayName("should return answer")
	void testGetAnswer() {
		given(ioService.getInput()).willReturn("answer");
		Question question = new Question("Question", "1", new ArrayList<>(0));
		assertEquals("answer", frontendService.getAnswer(question));
	}

	@Test
	@DisplayName("should print result")
	void testPrintResult() {
		final Map<Question, String> answerMap = Map.of(
				new Question("Question#1", "Correct answer", Collections.emptyList()), "Correct answer",
				new Question("Question#1", "Correct answer", Collections.emptyList()), "Incorrect answer"
		);

		frontendService.printResult("Test name", answerMap);
		verify(ioService, times(1)).print("Test passed by student: Test name\nThe percentage of correct answers: 50.0%\n");
	}
}
