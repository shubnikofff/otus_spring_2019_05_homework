package ru.otus.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Tourist {

	private final String name;

	private final Country arrivedFrom;

	@Setter
	private boolean infected;

	public Tourist(String name, Country arrivedFrom) {
		this.name = name;
		this.arrivedFrom = arrivedFrom;
	}
}
