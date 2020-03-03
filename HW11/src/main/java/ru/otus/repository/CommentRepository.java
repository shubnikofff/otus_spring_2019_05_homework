package ru.otus.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.domain.model.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

	Flux<Comment> findByBookId(String book_id);
}
