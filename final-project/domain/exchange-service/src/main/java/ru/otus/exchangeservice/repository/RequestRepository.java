package ru.otus.exchangeservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.exchangeservice.model.Request;

import java.util.Collection;

public interface RequestRepository extends MongoRepository<Request, String> {

	Collection<Request> findByUser(String user);

	Collection<Request> findByRequestedBookIdIn(Collection<String> ids);
}
