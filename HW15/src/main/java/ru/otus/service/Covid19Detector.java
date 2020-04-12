package ru.otus.service;

import ru.otus.domain.Tourist;

public interface Covid19Detector {

	Tourist check(Tourist tourist) throws InterruptedException;
}
