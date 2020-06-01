package ru.otus.pictureservice.model;

import lombok.Data;
import org.bson.Document;

import java.io.InputStream;

@Data
public class Picture {

	private final InputStream inputStream;

	private final String fileName;

	private final String contentType;

	private final Document metaData;
}
