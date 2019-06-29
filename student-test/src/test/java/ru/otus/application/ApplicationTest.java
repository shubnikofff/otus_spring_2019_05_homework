package ru.otus.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import ru.otus.domain.service.FrontendService;
import ru.otus.domain.service.QuestionDao;

class ApplicationTest {

	private Application application;

	@BeforeEach
	void setUp() {
		QuestionDao mockQuestionDao = mock(QuestionDao.class);
		FrontendService mockFrontendService = mock(FrontendService.class);
		application = new Application(mockQuestionDao, mockFrontendService);
	}

	@Test
	void runWithoutExceptions() {
		Assertions.assertDoesNotThrow(application::run);
	}
}
