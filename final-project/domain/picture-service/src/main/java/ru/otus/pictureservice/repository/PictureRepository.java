package ru.otus.pictureservice.repository;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.gridfs.GridFsResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;

public interface PictureRepository {

	Optional<GridFsResource> getResource(String id);

	Collection<GridFSFile> findByBookId(String bookId);

	ObjectId store(InputStream inputStream, String fileName, String contentType, Document metadata) throws IOException;

	void delete(String id);

	void deleteByBookId(String bookId);
}
