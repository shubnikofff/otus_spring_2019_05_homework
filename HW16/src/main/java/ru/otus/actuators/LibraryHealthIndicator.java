package ru.otus.actuators;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.otus.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class LibraryHealthIndicator implements HealthIndicator {

	private final BookRepository repository;

	@Override
	public Health health() {
		final long bookQuantity = repository.count();

		return bookQuantity > 0
				? Health.up().withDetail("bookQuantity", bookQuantity).build()
				: Health.down().withDetail("bookQuantity", bookQuantity).build();
	}
}
