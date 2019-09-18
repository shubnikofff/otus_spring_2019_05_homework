package ru.otus.application.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.StudentTest;
import ru.otus.domain.model.Answer;
import ru.otus.domain.model.Question;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = StudentTest.class)
public class QuestionDaoCsvTest {
	@Autowired
	private QuestionDaoCsv questionDaoCsv;

	@Test
	public void getCorrectSizeOfQuestions() throws IOException {
		assertEquals(2, questionDaoCsv.getQuestions().size());
	}

	@Test
	public void getQuestionsWithCorrectWording() throws IOException {
		Question question = questionDaoCsv.getQuestions().get(0);
		assertEquals("Question one", question.getWording());
	}

	@Test
	public void getQuestionsWithCorrectAnswerId() throws IOException {
		Question question = questionDaoCsv.getQuestions().get(0);
		assertEquals("1", question.getCorrectAnswer());
	}

	@Test
	public void getQuestionsWithCorrectAnswers() throws IOException {
		Map<String, Answer> answers = questionDaoCsv.getQuestions().get(0).getAnswers();
		assertEquals("1", answers.get("1").getId());
		assertEquals("Answer one", answers.get("1").getWording());
		assertEquals("2", answers.get("2").getId());
		assertEquals("Answer two", answers.get("2").getWording());
	}
}
