package ru.otus.application.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.StudentTest;
import ru.otus.application.utility.ConsoleUtility;
import ru.otus.domain.model.Question;
import ru.otus.domain.service.FrontendService;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = StudentTest.class)
public class FrontendServiceImplementationTest {
	@MockBean
	ConsoleUtility consoleUtility;

	@Autowired
	private FrontendService frontendService;

	@Test
	public void testGetFirstName() {
		given(consoleUtility.getUserInput()).willReturn("FirstName");
		assertEquals("FirstName", frontendService.getFirstName());
	}

	@Test
	public void testGetLastName() {
		given(consoleUtility.getUserInput()).willReturn("LastName");
		assertEquals("LastName", frontendService.getLastName());
	}

	@Test
	public void testGetAnswer() {
		given(consoleUtility.getUserInput()).willReturn("answer");
		Question question = new Question("Question", "1", new ArrayList<>(0));
		assertEquals("answer", frontendService.getAnswer(question));
	}
}
