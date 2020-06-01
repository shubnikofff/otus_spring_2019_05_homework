package ru.otus.userregistry.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.userregistry.model.Profile;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}
