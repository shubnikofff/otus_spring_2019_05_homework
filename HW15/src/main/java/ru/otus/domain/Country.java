package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum  Country {

	CHINA("China", 65.1F),

	ESTONIA("Estonia", 21.7F),

	SPAIN("Spain", 89.6F),

	URUGUAY("Uruguay", 10.2F),

	USA("USA", 90.0F);

	private final String name;

	private final float covid19Rate;
}
