package ru.otus.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.domain.Tourist;

import java.util.Collection;

@MessagingGateway
public interface ArrivalTerminal {

	@Gateway(requestChannel = "entrance", replyChannel = "exit")
	Collection<Tourist> accept(Collection<Tourist> tourists);
}
