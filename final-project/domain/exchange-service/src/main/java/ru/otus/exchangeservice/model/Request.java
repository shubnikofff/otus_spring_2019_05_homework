package ru.otus.exchangeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.Date;

@Document(collection = "requests")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Request {

	@Id
	private String id;

	private String requestedBookId;

	private Collection<String> offeredBookIds;

	private String additionalInfo;

	private String user;

	private Date requestDate;
}
