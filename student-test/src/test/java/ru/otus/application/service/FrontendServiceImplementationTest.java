package ru.otus.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ru.otus.domain.model.Question;
import ru.otus.domain.model.TestResults;
import ru.otus.domain.service.FrontendService;
import ru.otus.domain.service.IOService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	@DisplayName("should return test results")
	void testGetTestResults() {
		given(ioService.getInput()).willReturn("answer");
		List<Question> questions = List.of(
				new Question("Question1", "answer", new ArrayList<>(0)),
				new Question("Question2", "answer", new ArrayList<>(0))
		);

		final TestResults testResults = frontendService.getTestResults(questions);
		assertEquals(testResults.size(), 2);
		verify(ioService, times(2)).getInput();
	}

	@Test
	@DisplayName("should print test result")
	void testPrintResult() {
		final TestResults testResults = new TestResults();
		testResults.addAnswer(new Question("Question#1", "Correct answer", Collections.emptyList()), "Correct answer");
		testResults.addAnswer(new Question("Question#1", "Correct answer", Collections.emptyList()), "Incorrect answer");

		frontendService.printTestResults("Ivan", "Ivanov", testResults);
		verify(ioService, times(1)).print("Test passed by student: Ivan Ivanov\nThe percentage of correct answers: 50.0%\n");
	}
}
