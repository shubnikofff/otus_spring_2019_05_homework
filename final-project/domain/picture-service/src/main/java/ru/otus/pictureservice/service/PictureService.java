package ru.otus.pictureservice.service;

import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.pictureservice.dto.PictureMetadataDto;

import java.io.IOException;
import java.util.Collection;

public interface PictureService {

	GridFsResource getResource(String id);

	Collection<PictureMetadataDto> getByBookId(String bookId);

	PictureMetadataDto save(MultipartFile multipartFile, String bookId) throws IOException;

	void delete(String id);
}
