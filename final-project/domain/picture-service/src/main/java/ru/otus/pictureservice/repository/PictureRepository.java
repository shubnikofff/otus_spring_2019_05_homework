package ru.otus.pictureservice.repository;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import ru.otus.pictureservice.model.Picture;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public interface PictureRepository {

	Optional<GridFsResource> getResource(String id);

	Collection<GridFSFile> findByBookId(String bookId);

	ObjectId save(Picture picture) throws IOException;

	void delete(String id);

	void deleteByBookId(String bookId);
}
