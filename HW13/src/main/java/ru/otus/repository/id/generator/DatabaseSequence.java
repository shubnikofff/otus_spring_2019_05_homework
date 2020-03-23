package ru.otus.repository.id.generator;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@Document(collection = "database_sequences")
public class DatabaseSequence {

	@Id
	private String id;

	private Long value;
}
