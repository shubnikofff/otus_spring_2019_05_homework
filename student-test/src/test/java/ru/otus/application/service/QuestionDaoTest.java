package ru.otus.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.domain.model.Answer;
import ru.otus.domain.model.Question;
import ru.otus.domain.service.QuestionDao;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("QuestionDaoScv service")
class QuestionDaoTest {
	@Autowired
	private QuestionDao questionDao;

	@Test
	@DisplayName("should return correct quantity of questions")
	void getCorrectSizeOfQuestions() throws IOException {
		assertEquals(2, questionDao.getQuestions().size());
	}

	@Test
	@DisplayName("should return questions with correct wording")
	void getQuestionsWithCorrectWording() throws IOException {
		Question question = questionDao.getQuestions().get(0);
		assertEquals("Question one", question.getWording());
	}

	@Test
	@DisplayName("should return questions with correct answer id")
	void getQuestionsWithCorrectAnswerId() throws IOException {
		Question question = questionDao.getQuestions().get(0);
		assertEquals("1", question.getCorrectAnswer());
	}

	@Test
	@DisplayName("should return questions with correct answers")
	void getQuestionsWithCorrectAnswers() throws IOException {
		Map<String, Answer> answers = questionDao.getQuestions().get(0).getAnswers();
		assertEquals("1", answers.get("1").getId());
		assertEquals("Answer one", answers.get("1").getWording());
		assertEquals("2", answers.get("2").getId());
		assertEquals("Answer two", answers.get("2").getWording());
	}
}
