package ru.otus.application.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.domain.model.Answer;
import ru.otus.domain.model.Question;
import ru.otus.domain.service.QuestionService;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionServiceImplementationTest {
	@Autowired
	public QuestionService questionService;
	private Question question;

	@Before
	public void setUp() {
		ArrayList<Answer> answers = new ArrayList<>(2);
		answers.add(new Answer("1", "Answer 1"));
		answers.add(new Answer("2", "Answer 2"));
		question = new Question("Question", "1", answers);
	}

	@Test
	public void stringifyQuestion() {
		String expected = "Question\n" +
				"\t1. Answer 1\n" +
				"\t2. Answer 2\n";
		assertEquals(expected, questionService.stringifyQuestion(question));
	}
}
