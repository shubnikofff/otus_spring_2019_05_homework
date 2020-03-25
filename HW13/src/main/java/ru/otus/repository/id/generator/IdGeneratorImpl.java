package ru.otus.repository.id.generator;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
@Service
public class IdGeneratorImpl implements IdGenerator {

	private final MongoOperations mongoOperations;

	@Override
	public Long getId(String sequenceName) {
		final DatabaseSequence sequence = mongoOperations.findAndModify(
				query(where("_id").is(sequenceName)), new Update().inc("value", 1L),
				options().returnNew(true).upsert(true),
				DatabaseSequence.class);

		return Optional.ofNullable(sequence).map(DatabaseSequence::getValue).orElse(1L);
	}
}
