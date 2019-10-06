package ru.otus.domain.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestResults implements Iterable<Map.Entry<Question, String>> {
	private final Map<Question, String> answerMap = new HashMap<>();

	public void addAnswer(Question question, String answer) {
		answerMap.put(question, answer);
	}

	@Override
	public Iterator<Map.Entry<Question, String>> iterator() {
		return answerMap.entrySet().iterator();
	}

	public int size() {
		return answerMap.size();
	}
}
