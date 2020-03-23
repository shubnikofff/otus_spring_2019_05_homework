package ru.otus.repository.id.generator;

public interface IdGenerator {

	Long getId(String sequenceName);
}
