package ru.otus.compositeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@AllArgsConstructor
@Getter
public class BookWithUserProfileDto {

	private final String id;

	private final String title;

	private final String genre;

	private final Collection<String> authors;

	private final UserProfileDto owner;
}
