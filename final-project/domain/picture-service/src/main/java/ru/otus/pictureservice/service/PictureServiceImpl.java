package ru.otus.pictureservice.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.pictureservice.dto.PictureMetadataDto;
import ru.otus.pictureservice.exception.PictureNotFoundException;
import ru.otus.pictureservice.repository.PictureRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {

	private final PictureRepository pictureRepository;

	@HystrixCommand(commandKey = "getPictureResource", fallbackMethod = "getResourceFallback")
	@Override
	public GridFsResource getResource(String id) {
		return pictureRepository.getResource(id).orElseThrow(PictureNotFoundException::new);
	}

	private GridFsResource getResourceFallback(String id) throws IOException {
		final GridFSFile file = new GridFSFile(
				new BsonString(id),
				"book.jpg",
				61286L,
				1024,
				new Date(),
				"C69F253E6E5E5D6F4AE0B0F7BA994CD8",
				new Document("_contentType", "image/jpeg")
		);

		return new GridFsResource(file, new ClassPathResource("book.jpg").getInputStream());
	}

	@HystrixCommand(commandKey = "getPicturesByBookID", fallbackMethod = "getByBookIdFallback")
	@Override
	public Collection<PictureMetadataDto> getByBookId(String bookId) {
		return pictureRepository.findByBookId(bookId).stream()
				.map(picture -> new PictureMetadataDto(
						picture.getId().asObjectId().getValue().toHexString(),
						picture.getFilename(),
						picture.getUploadDate().toString()
				))
				.collect(Collectors.toList());
	}

	private Collection<PictureMetadataDto> getByBookIdFallback() {
		return Collections.emptyList();
	}

	@HystrixCommand(commandKey = "savePicture", fallbackMethod = "emptyFallback")
	@Override
	public PictureMetadataDto save(MultipartFile multipartFile, String bookId) throws IOException {
		final ObjectId id = pictureRepository.store(
				multipartFile.getInputStream(),
				multipartFile.getOriginalFilename(),
				multipartFile.getContentType(),
				new Document("bookId", bookId)
		);

		return new PictureMetadataDto(id.toString(), multipartFile.getOriginalFilename(), new Date().toString());
	}

	@HystrixCommand(commandKey = "deletePicture", fallbackMethod = "emptyFallback")
	@Override
	public void delete(String id) {
		pictureRepository.delete(id);
	}

	private void emptyFallback() {
	}
}
