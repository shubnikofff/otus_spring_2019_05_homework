package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Tourist {

	private final String name;

	private final Country arrivedFrom;

	@Override
	public String toString() {
		return name + " arrived from " + arrivedFrom.getName();
	}
}
