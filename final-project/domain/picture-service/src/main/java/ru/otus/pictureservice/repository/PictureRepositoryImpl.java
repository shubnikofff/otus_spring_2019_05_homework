package ru.otus.pictureservice.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class PictureRepositoryImpl implements PictureRepository {

	private final GridFsOperations gridFsOperations;

	@Override
	public Optional<GridFsResource> getResource(String id) {
		final GridFSFile file = gridFsOperations.findOne(query(where("_id").is(id)));
		return Optional.ofNullable(file).map(gridFsOperations::getResource);
	}

	@Override
	public Collection<GridFSFile> findByBookId(String bookId) {
		final Collection<GridFSFile> result = new ArrayList<>();
		gridFsOperations.find(query(where("metadata.bookId").is(bookId)))
				.sort(new BasicDBObject("uploadDate", -1))
				.into(result);
		return result;
	}

	@Override
	public ObjectId store(InputStream inputStream, String fileName, String contentType, Document metadata) {
		return gridFsOperations.store(inputStream, fileName, contentType, metadata);
	}

	@Override
	public void delete(String id) {
		gridFsOperations.delete(query(where("_id").is(id)));
	}

	@Override
	public void deleteByBookId(String bookId) {
		gridFsOperations.delete(query(where("bookId").is(bookId)));
	}
}
