package ru.otus.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.domain.service.QuestionDao;
import ru.otus.domain.model.Answer;
import ru.otus.domain.model.Question;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class QuestionDaoCsv implements QuestionDao {
	private final String csvPath;
	private final String defaultLocale;
	private final String separator;
	private final String answerIdSeparator;

	public QuestionDaoCsv(
			@Value("${csv.path}") String csvPath,
			@Value("${locale.default}") String defaultLocale,
			@Value("${separator.main}") String separator,
			@Value("${separator.answer_id}") String answerIdSeparator
	) {
		this.csvPath = csvPath;
		this.defaultLocale = defaultLocale;
		this.separator = separator;
		this.answerIdSeparator = answerIdSeparator;
	}

	private Question mapQuestions(String raw) {
		final String[] dividedRaw = raw.split(separator);
		final String wording = dividedRaw[0];
		final String correctAnswer = dividedRaw[1];
		final List<Answer> answers = Arrays.stream(dividedRaw).skip(2).map(this::mapAnswers).collect(Collectors.toList());

		return new Question(wording, correctAnswer, answers);
	}

	private Answer mapAnswers(String raw) {
		final String[] dividedRaw = raw.split(answerIdSeparator);
		final String id = dividedRaw[0];
		final String wording = dividedRaw[1];

		return new Answer(id, wording);
	}

	@Override
	public List<Question> getQuestions() throws IOException {
		final InputStream stream = getClass().getClassLoader().getResourceAsStream(getResourcePath());
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(stream)))) {
			return bufferedReader.lines().map(this::mapQuestions).collect(Collectors.toList());
		}
	}

	private String getResourcePath() {
		return csvPath + File.separator + defaultLocale + ".csv";
	}
}
