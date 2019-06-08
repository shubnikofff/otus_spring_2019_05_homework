package ru.otus.application.dao;

import ru.otus.dao.QuestionDao;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class QuestionDaoCsv implements QuestionDao {
	private final static String COMMA_SEPARATOR = ",";
	private final static String ANSWER_ID_SEPARATOR = "#";

	private final String resourcePath;

	public QuestionDaoCsv(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	private final Function<String, Question> questionMapper = (raw) -> {
		final String[] dividedRaw = raw.split(COMMA_SEPARATOR);
		final String wording = dividedRaw[0];
		final String correctAnswer = dividedRaw[1];
		final List<Answer> answers = Arrays.stream(dividedRaw).skip(2).map(this.answerMapper).collect(Collectors.toList());

		return new Question(wording, correctAnswer, answers);
	};

	private final Function<String, Answer> answerMapper = (raw) -> {
		final String[] dividedRaw = raw.split(ANSWER_ID_SEPARATOR);
		final String id = dividedRaw[0];
		final String wording = dividedRaw[1];

		return new Answer(id, wording);
	};

	@Override
	public List<Question> getQuestions() throws IOException {
		final InputStream stream = getClass().getClassLoader().getResourceAsStream(resourcePath);
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(stream)))) {
			return bufferedReader.lines().map(questionMapper).collect(Collectors.toList());
		}
	}
}
