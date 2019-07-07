package ru.otus.domain.service;

import ru.otus.domain.model.Question;

import java.io.IOException;
import java.util.List;

public interface QuestionDao {
	List<Question> getQuestions() throws IOException;
}
