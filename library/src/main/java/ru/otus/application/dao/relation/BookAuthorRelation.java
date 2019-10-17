package ru.otus.application.dao.relation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookAuthorRelation {
	private final long bookId;
	private final long authorId;
}
