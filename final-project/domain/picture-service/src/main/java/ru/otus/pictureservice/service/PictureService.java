package ru.otus.pictureservice.service;

import com.mongodb.gridfs.GridFSFile;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.pictureservice.dto.PictureMetadataDto;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public interface PictureService {

	GridFsResource getResource(String id);

	Collection<PictureMetadataDto> getByBookId(String bookId);

	Map<String, Collection<PictureMetadataDto>> getAllLastUploadedByBookIds(Collection<String> bookIds);

	PictureMetadataDto save(MultipartFile multipartFile, String bookId) throws IOException;

	void delete(String id);

	void deleteByBookId(String bookId);
}
