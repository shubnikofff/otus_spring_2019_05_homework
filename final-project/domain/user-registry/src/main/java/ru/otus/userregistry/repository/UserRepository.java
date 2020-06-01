package ru.otus.userregistry.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.userregistry.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> getByUsername(String username);

	Collection<User> getAllByUsernameIn(Collection<String> usernames);
}
